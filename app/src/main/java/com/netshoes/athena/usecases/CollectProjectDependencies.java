package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.domains.ScmRepositoryContent;
import com.netshoes.athena.gateways.CouldNotGetRepositoryContentException;
import com.netshoes.athena.gateways.DependencyManagerGateway;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.usecases.exceptions.DependencyCollectException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CollectProjectDependencies {

  private static final String VALID_DESCRIPTORS[] = new String[] {"pom.xml"};
  private static final int MAX_DIRECTORY_DEPTH = 1;
  private final ScmGateway scmGateway;
  private final DependencyManagerGateway dependencyManagerGateway;
  private final ProjectGateway projectGateway;

  public void execute(String repositoryId, String branch) throws DependencyCollectException {
    final ScmRepository repository;
    try {
      repository = scmGateway.getRepository(repositoryId);
    } catch (Exception e) {
      throw new DependencyCollectException(e);
    }
    final Project project = new Project(repository, branch);
    execute(project);
  }

  public void execute(Project project) throws DependencyCollectException {
    log.info(
        "Starting analysis of repository {} in branch {} ...",
        project.getScmRepository().getId(),
        project.getBranch());

    List<ScmRepositoryContent> descriptorsContent = null;
    try {
      descriptorsContent = findDependencyManagerDescriptors(project);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    if (descriptorsContent != null && !descriptorsContent.isEmpty()) {
      logDescriptorsContent(descriptorsContent);
      try {
        final List<DependencyManagementDescriptor> descriptors =
            dependencyManagerGateway.analyze(descriptorsContent);

        descriptors.forEach(descriptor -> project.addDependencyManagerDescriptor(descriptor));

        projectGateway.save(project);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
  }

  private void logDescriptorsContent(List<ScmRepositoryContent> descriptorsContent) {
    if (log.isDebugEnabled()) {
      for (ScmRepositoryContent descriptor : descriptorsContent) {
        if (descriptor.getPath().equals(descriptor.getName())) {
          log.debug("Found {} in {}.", descriptor.getName(), descriptor.getRepository().getId());
        } else {
          log.debug(
              "Found {}/{} in {}.",
              descriptor.getPath(),
              descriptor.getName(),
              descriptor.getRepository().getId());
        }
      }
    }
  }

  private List<ScmRepositoryContent> findDependencyManagerDescriptors(Project project)
      throws CouldNotGetRepositoryContentException {

    final List<ScmRepositoryContent> descriptors = new ArrayList<>();

    final ScmRepository scmRepository = project.getScmRepository();
    final String branch = project.getBranch();
    final List<ScmRepositoryContent> rootContents =
        scmGateway.getContents(scmRepository, branch, "/");

    findDependencyManagerDescriptorRecursive(project, "/", rootContents, descriptors, 0);

    for (ScmRepositoryContent descriptor : descriptors) {
      scmGateway.retrieveContent(descriptor);
    }
    return descriptors;
  }

  private void findDependencyManagerDescriptorRecursive(
      Project project,
      String path,
      List<ScmRepositoryContent> contents,
      List<ScmRepositoryContent> descriptorsFounded,
      int depth)
      throws CouldNotGetRepositoryContentException {

    final ScmRepository scmRepository = project.getScmRepository();
    final String branch = project.getBranch();
    final List<ScmRepositoryContent> innerContents = new ArrayList<>();

    depth++;
    if (depth <= MAX_DIRECTORY_DEPTH) {
      if ("/".equals(path)) {
        path = "";
      }
      log.debug(
          "Searching dependency manager descriptor in {} for {} ...",
          "".equals(path) ? "root" : path,
          scmRepository.getId());

      for (ScmRepositoryContent content : contents) {
        if (content.isDirectory()) {
          final String newPath = path + "/" + content.getName();
          final List<ScmRepositoryContent> childContents =
              scmGateway.getContents(scmRepository, branch, newPath);

          findDependencyManagerDescriptorRecursive(
              project, newPath, childContents, innerContents, depth);
        } else {
          innerContents.add(content);
        }
      }
      descriptorsFounded.addAll(filterDependencyDescriptors(innerContents));
    }
  }

  private List<ScmRepositoryContent> filterDependencyDescriptors(
      List<ScmRepositoryContent> contents) {
    return contents
        .stream()
        .filter(content -> ArrayUtils.contains(VALID_DESCRIPTORS, content.getName()))
        .collect(Collectors.toList());
  }
}
