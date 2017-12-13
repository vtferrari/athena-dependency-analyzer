package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.Technology;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AnalyzeProjectDependencies {
  private final AnalyzeArtifact analyzeArtifact;
  private final ProjectGateway projectGateway;

  public Project execute(String projectId) throws ProjectNotFoundException {
    final Optional<Project> opProject = projectGateway.findById(projectId);

    if (!opProject.isPresent()) {
      throw new ProjectNotFoundException(projectId);
    }

    final Project project = opProject.get();
    final Set<DependencyManagementDescriptor> descriptors = project.getDescriptors();
    descriptors.forEach(x -> execute(project, x));
    return project;
  }

  private void execute(Project project, DependencyManagementDescriptor descriptor) {
    descriptor
        .getArtifacts()
        .forEach(
            artifact -> {
              artifact.addRelatedTechnologies(Technology.discover(artifact));
              final Optional<ArtifactVersionReport> opReport = analyzeArtifact.execute(artifact);
              opReport.ifPresent(
                  report -> {
                    log.info(
                        "Artifact {} in project {} analyzed: {}",
                        artifact,
                        project,
                        report.getSummary());

                    artifact.setReport(report);
                    projectGateway.save(project);
                  });
            });
  }
}
