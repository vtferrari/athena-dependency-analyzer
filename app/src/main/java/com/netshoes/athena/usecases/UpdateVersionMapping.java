package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.usecases.exceptions.VersionMappingNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateVersionMapping {
  private final VersionMappingGateway versionMappingGateway;

  public Mono<VersionMapping> execute(VersionMapping versionMapping) {
    final Mono<VersionMapping> save = versionMappingGateway.save(versionMapping);
    return versionMappingGateway
        .findById(versionMapping.getId())
        .switchIfEmpty(
            Mono.defer(
                () -> Mono.error(new VersionMappingNotFoundException(versionMapping.getId()))))
        .then(save)
        .doOnSuccess(
            saved ->
                log.info("VersionMapping {}:{} saved", saved.getGroupId(), saved.getArtifactId()));
  }
}
