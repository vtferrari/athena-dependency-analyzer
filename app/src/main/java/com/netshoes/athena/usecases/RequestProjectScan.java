package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.GetRepositoryException;
import com.netshoes.athena.gateways.PendingProjectAnalyzeGateway;
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
  private final PendingProjectAnalyzeGateway pendingProjectAnalyzeGateway;
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
          final Project project = new Project(repository, repository.getMasterBranch());

          final PendingProjectAnalyze pendingProjectAnalyze =
              pendingProjectAnalyzeGateway.findById(project.getId());
          if (pendingProjectAnalyze == null) {
            asynchronousProcessGateway.requestDependencyAnalyze(project);
          } else {
            log.info(
                "Project {} already scheduled for scan, ignoring request...", project.getName());
          }
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
