package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.VersionMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VersionMappingGateway {
  Mono<VersionMapping> findById(String id);

  Flux<VersionMapping> findAll();

  Mono<VersionMapping> findByArtifact(Artifact artifact);

  Mono<VersionMapping> save(VersionMapping versionMapping);

  Mono<Void> delete(String id);
}
