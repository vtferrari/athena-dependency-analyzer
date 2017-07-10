package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.GetRepositoryException;
import com.netshoes.athena.gateways.ScmGateway;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequestCollectProjects {

  private final ScmGateway scmGateway;
  private final AsynchronousProcessGateway asynchronousProcessGateway;

  public List<Project> forMasterBranchToAllProjectsFromConfiguredOwner() throws RequestCollectProjectException {
    List<ScmRepository> repositories;
    try {
      repositories = scmGateway.getRepositoriesFromConfiguredOwner();
    } catch (GetRepositoryException e) {
      throw new RequestCollectProjectException(e);
    }

    final List<Project> projects = new ArrayList<>();
    repositories.forEach(
        repository -> {
          final Project project = new Project(repository, repository.getMasterBranch());
          asynchronousProcessGateway.requestDependencyAnalyze(project);
          projects.add(project);
        });
    return projects;
  }
}
