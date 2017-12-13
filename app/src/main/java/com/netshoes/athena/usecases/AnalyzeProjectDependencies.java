package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.Technology;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
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
    final AtomicBoolean projectModified = new AtomicBoolean();
    descriptor
        .getArtifacts()
        .forEach(
            artifact -> {
              final Set<Technology> actualTechnologies =
                  new HashSet<>(artifact.getRelatedTechnologies());
              final Set<Technology> technologiesDiscovered = Technology.discover(artifact);

              boolean artifactModified = false;
              if (!actualTechnologies.equals(technologiesDiscovered)) {
                artifact.addRelatedTechnologies(technologiesDiscovered);
                artifactModified = true;
              }

              final Optional<ArtifactVersionReport> opActualReport = artifact.getReport();
              final Optional<ArtifactVersionReport> opReport = analyzeArtifact.execute(artifact);

              if (!opActualReport.equals(opReport)) {
                artifactModified = true;
                if (opReport.isPresent()) {
                  final ArtifactVersionReport report = opReport.get();
                  artifact.setReport(report);
                  log.info(
                      "Report generated for artifact {} in project {}. Summary: {}",
                      artifact,
                      project,
                      report.getSummary());
                } else {
                  artifact.setReport(null);
                }
              }
              if (artifactModified) {
                projectModified.set(true);
              }
            });

    if (projectModified.get()) {
      projectGateway.save(project);
    }
  }
}
