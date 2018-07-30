package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.RequestOfPage;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetProjects {

  private final ProjectGateway projectGateway;

  public Flux<Project> all(RequestOfPage requestOfPage) {
    return projectGateway.findAll(requestOfPage);
  }

  public Flux<Project> search(RequestOfPage requestOfPage, String name) {
    return projectGateway.findByNameContaining(requestOfPage, name);
  }

  public Mono<Long> countSearch() {
    return projectGateway.count();
  }

  public Mono<Long> countSearch(String name) {
    return projectGateway.countByNameContaining(name);
  }

  public Mono<Project> byId(String id) {
    final Mono<Project> project = projectGateway.findById(id);
    return project.switchIfEmpty(Mono.defer(() -> Mono.error(new ProjectNotFoundException(id))));
  }
}
