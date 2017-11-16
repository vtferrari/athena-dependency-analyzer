package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.GetRepositoryException;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import com.netshoes.athena.usecases.exceptions.RequestScanException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequestProjectScan {

  private final ScmGateway scmGateway;
  private final GetProjects getProjects;
  private final AsynchronousProcessGateway asynchronousProcessGateway;

  public List<Project> forMasterBranchToAllProjectsFromConfiguredOrganization()
      throws RequestScanException {
    List<ScmRepository> repositories;
    try {
      repositories = scmGateway.getRepositoriesFromConfiguredOrganization();
    } catch (GetRepositoryException e) {
      throw new RequestScanException(e);
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

  public Project refresh(String projectId) throws ProjectNotFoundException {
    final Project project = getProjects.byId(projectId);
    asynchronousProcessGateway.requestDependencyAnalyze(project);
    return project;
  }
}
