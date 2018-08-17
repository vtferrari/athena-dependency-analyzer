package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ProjectFilter;
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

  public Flux<Project> search(RequestOfPage requestOfPage, ProjectFilter filter) {
    return projectGateway.findAll(requestOfPage, filter);
  }

  public Flux<Project> byArtifact(ArtifactFilter filter) {
    return projectGateway.findByDescriptorsArtifacts(filter);
  }

  public Mono<Long> countSearch() {
    return projectGateway.count();
  }

  public Mono<Long> countSearch(ProjectFilter filter) {
    return projectGateway.count(filter);
  }

  public Mono<Project> byId(String id) {
    return projectGateway
        .findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new ProjectNotFoundException(id))));
  }
}
