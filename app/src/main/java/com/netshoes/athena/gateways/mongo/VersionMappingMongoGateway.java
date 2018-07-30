package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.gateways.mongo.docs.VersionMappingDoc;
import com.netshoes.athena.gateways.mongo.repositories.VersionMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class VersionMappingMongoGateway implements VersionMappingGateway {
  private final VersionMappingRepository versionMappingRepository;

  @Override
  public Mono<VersionMapping> findById(String id) {
    return versionMappingRepository.findById(id).map(VersionMappingDoc::toDomain);
  }

  @Override
  public Mono<VersionMapping> findByArtifact(Artifact artifact) {
    return Mono.just(artifact)
        .map(a -> VersionMapping.generateId(a.getGroupId(), a.getArtifactId()))
        .flatMap(versionMappingRepository::findById)
        .map(VersionMappingDoc::toDomain);
  }

  @Override
  public Flux<VersionMapping> findAll() {
    return versionMappingRepository.findAll().map(VersionMappingDoc::toDomain);
  }

  @Override
  public Mono<VersionMapping> save(VersionMapping versionMapping) {
    return Mono.just(versionMapping)
        .map(VersionMappingDoc::new)
        .flatMap(versionMappingRepository::save)
        .map(VersionMappingDoc::toDomain);
  }

  @Override
  public Mono<Void> delete(String id) {
    return versionMappingRepository.deleteById(id).then();
  }
}
