package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.PendingProjectAnalyze;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.domains.ScmRepositoryContent;
import com.netshoes.athena.gateways.CouldNotGetRepositoryContentException;
import com.netshoes.athena.gateways.DependencyManagerGateway;
import com.netshoes.athena.gateways.PendingProjectAnalyzeGateway;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.gateways.ScmApiGatewayRateLimitExceededException;
import com.netshoes.athena.gateways.ScmApiGetRateLimitException;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import com.netshoes.athena.usecases.exceptions.ProjectScanException;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectScan {

  private static final String VALID_DESCRIPTORS[] = new String[] {"pom.xml"};
  private static final int MAX_DIRECTORY_DEPTH = 1;
  private final ScmGateway scmGateway;
  private final DependencyManagerGateway dependencyManagerGateway;
  private final ProjectGateway projectGateway;
  private final PendingProjectAnalyzeGateway pendingProjectAnalyzeGateway;

  public Project execute(String projectId, String repositoryId, String branch) {
    final Optional<Project> opProject = projectGateway.findById(projectId);
    Project project = null;
    try {
      if (!opProject.isPresent()) {
        project = createProjectFromScm(repositoryId, branch);
      } else {
        project = opProject.get();
      }
      project = execute(project);
    } catch (Exception e) {
      scheduleToLater(repositoryId, branch, e);
    }
    return project;
  }

  private void scheduleToLater(String repositoryId, String branch, Exception e) {
    final ScmRepository scmRepository = new ScmRepository();
    scmRepository.setId(repositoryId);

    final Project project = new Project(scmRepository, branch);

    LocalDateTime scheduledDate;
    try {
      final OffsetDateTime resetRateLimit = scmGateway.getRateLimit().getRate().getReset();
      scheduledDate = resetRateLimit.toLocalDateTime();
    } catch (ScmApiGetRateLimitException e1) {
      scheduledDate = LocalDateTime.now().plusMinutes(5);
      log.warn("Could not get rate limit from SCM API.", e);
    }
    final PendingProjectAnalyze pendingProjectAnalyze = new PendingProjectAnalyze(project);
    pendingProjectAnalyze.setException(e);
    pendingProjectAnalyze.setScheduledDate(scheduledDate);
    pendingProjectAnalyzeGateway.save(pendingProjectAnalyze);

    log.warn(
        "Project analyze scheduled to {}. Id: {}, Name: {}",
        scheduledDate,
        project.getId(),
        project.getName(),
        e);
  }

  private Project createProjectFromScm(String repositoryId, String branch)
      throws ProjectScanException {
    final ScmRepository repository;
    try {
      repository = scmGateway.getRepository(repositoryId);
    } catch (Exception e) {
      throw new ProjectScanException(e);
    }
    return new Project(repository, branch);
  }

  public Project execute(String projectId)
      throws ProjectNotFoundException, ProjectScanException, ScmApiRateLimitExceededException {
    final Optional<Project> opProject = projectGateway.findById(projectId);
    if (!opProject.isPresent()) {
      throw new ProjectNotFoundException(projectId);
    }
    return execute(opProject.get());
  }

  public Project execute(Project project)
      throws ProjectScanException, ScmApiRateLimitExceededException {
    log.info(
        "Starting analysis of repository {} in branch {} ...",
        project.getScmRepository().getId(),
        project.getBranch());

    pendingProjectAnalyzeGateway.delete(project.getId());

    List<ScmRepositoryContent> descriptorsContent = null;
    try {
      descriptorsContent = findDependencyManagerDescriptors(project);
    } catch (CouldNotGetRepositoryContentException e) {
      log.error(e.getMessage(), e);
    }

    Project savedProject = null;
    if (descriptorsContent != null && !descriptorsContent.isEmpty()) {
      logDescriptorsContent(descriptorsContent);
      try {
        final List<DependencyManagementDescriptor> descriptors =
            dependencyManagerGateway.analyze(descriptorsContent);

        project.clearDependencyManagerDescriptors();
        descriptors.forEach(descriptor -> project.addDependencyManagerDescriptor(descriptor));

        savedProject = projectGateway.save(project);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
    return savedProject;
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
      throws CouldNotGetRepositoryContentException, ScmApiRateLimitExceededException {

    final List<ScmRepositoryContent> descriptors = new ArrayList<>();

    final ScmRepository scmRepository = project.getScmRepository();
    final String branch = project.getBranch();
    try {
      final List<ScmRepositoryContent> rootContents =
          scmGateway.getContents(scmRepository, branch, "/");

      findDependencyManagerDescriptorRecursive(project, "/", rootContents, descriptors, 0);

      for (ScmRepositoryContent descriptor : descriptors) {
        scmGateway.retrieveContent(descriptor);
      }
    } catch (ScmApiGatewayRateLimitExceededException e) {
      throw new ScmApiRateLimitExceededException(e, e.getMinutesToReset());
    }
    return descriptors;
  }

  private void findDependencyManagerDescriptorRecursive(
      Project project,
      String path,
      List<ScmRepositoryContent> contents,
      List<ScmRepositoryContent> descriptorsFounded,
      int depth)
      throws CouldNotGetRepositoryContentException, ScmApiRateLimitExceededException {

    final ScmRepository scmRepository = project.getScmRepository();
    final String branch = project.getBranch();

    if ("/".equals(path)) {
      path = "";
    }
    log.debug(
        "Searching dependency manager descriptor in {} (depth: {}) for {}  ...",
        "".equals(path) ? "root" : path,
        depth,
        scmRepository.getId());

    final List<ScmRepositoryContent> innerContents = new ArrayList<>();
    for (ScmRepositoryContent content : contents) {
      if (content.isDirectory()) {
        final int newDepth = depth + 1;
        if (newDepth <= MAX_DIRECTORY_DEPTH) {
          final String newPath = path + "/" + content.getName();
          final List<ScmRepositoryContent> childContents;
          try {
            childContents = scmGateway.getContents(scmRepository, branch, newPath);
          } catch (ScmApiGatewayRateLimitExceededException e) {
            throw new ScmApiRateLimitExceededException(e, e.getMinutesToReset());
          }
          findDependencyManagerDescriptorRecursive(
              project, newPath, childContents, descriptorsFounded, newDepth);
        }
      } else {
        innerContents.add(content);
      }
    }
    final List<ScmRepositoryContent> descriptors = filterDependencyDescriptors(innerContents);
    log.debug(
        "{} dependency(ies) manager(s) descriptor(s) found in {} (depth: {}) for {}  ...",
        descriptors.size(),
        "".equals(path) ? "root" : path,
        depth,
        scmRepository.getId());
    descriptorsFounded.addAll(descriptors);
  }

  private List<ScmRepositoryContent> filterDependencyDescriptors(
      List<ScmRepositoryContent> contents) {
    return contents
        .stream()
        .filter(content -> ArrayUtils.contains(VALID_DESCRIPTORS, content.getName()))
        .collect(Collectors.toList());
  }
}
