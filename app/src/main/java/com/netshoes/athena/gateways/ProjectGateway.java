package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ProjectFilter;
import com.netshoes.athena.domains.RequestOfPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectGateway {

  Mono<Project> findById(String id);

  Flux<Project> findByDescriptorsArtifacts(ArtifactFilter filter);

  Flux<Project> findAll();

  Flux<Project> findAll(RequestOfPage requestOfPage);

  Flux<Project> findAll(RequestOfPage requestOfPage, ProjectFilter filter);

  Mono<Project> save(Project project);

  Mono<Long> count();

  Mono<Long> count(ProjectFilter filter);
}
