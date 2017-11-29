package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.ProjectGateway;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequestProjectDependenciesAnalyze {
  private final ProjectGateway projectGateway;
  private final AsynchronousProcessGateway asynchronousProcessGateway;

  public void forAllProjects() {
    try (final Stream<Project> projects = projectGateway.readAll()) {
      projects.forEach(asynchronousProcessGateway::requestProjectDependencyAnalyze);
    }
  }
}
