package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.GetRepositoryException;
import com.netshoes.athena.gateways.ScmApiGatewayRateLimitExceededException;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import com.netshoes.athena.usecases.exceptions.RequestScanException;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RequestProjectScan {

  private final ScmGateway scmGateway;
  private final GetProjects getProjects;
  private final AsynchronousProcessGateway asynchronousProcessGateway;

  public List<Project> forMasterBranchToAllProjectsFromConfiguredOrganization()
      throws RequestScanException, ScmApiRateLimitExceededException {
    List<ScmRepository> repositories;
    try {
      repositories = scmGateway.getRepositoriesFromConfiguredOrganization();
    } catch (GetRepositoryException e) {
      throw new RequestScanException(e);
    } catch (ScmApiGatewayRateLimitExceededException e) {
      throw new ScmApiRateLimitExceededException(e, e.getMinutesToReset());
    }

    final List<Project> projects = new ArrayList<>();
    repositories.forEach(
        repository -> {
          final Project project = forBranchOfRepository(repository.getMasterBranch(), repository);
          projects.add(project);
        });
    return projects;
  }

  public Project forBranchOfRepository(String branch, ScmRepository repository) {
    final Project project = new Project(repository, branch);
    asynchronousProcessGateway.requestProjectScan(project);
    return project;
  }

  public Project refresh(String projectId) throws ProjectNotFoundException {
    final Project project = getProjects.byId(projectId);
    asynchronousProcessGateway.requestProjectScan(project);
    return project;
  }
}
