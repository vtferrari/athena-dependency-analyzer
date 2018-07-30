package com.netshoes.athena.usecases;

import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.ProjectGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RequestProjectDependenciesAnalyze {
  private final ProjectGateway projectGateway;
  private final AsynchronousProcessGateway asynchronousProcessGateway;

  public Mono<Void> forAllProjects() {
    return projectGateway
        .findAll()
        .flatMap(asynchronousProcessGateway::requestProjectDependencyAnalyze)
        .then();
  }
}
