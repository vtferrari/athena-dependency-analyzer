package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.usecases.exceptions.VersionMappingAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class CreateVersionMapping {
  private final VersionMappingGateway versionMappingGateway;

  public Mono<VersionMapping> execute(VersionMapping versionMapping) {
    return checkIfAlreadyExists(versionMapping)
        .flatMap(versionMappingGateway::save)
        .doOnSuccess(
            saved ->
                log.info("VersionMapping {}:{} saved", saved.getGroupId(), saved.getArtifactId()));
  }

  private Mono<VersionMapping> checkIfAlreadyExists(VersionMapping versionMapping) {
    return versionMappingGateway
        .findById(versionMapping.getId())
        .map(
            vm -> {
              Mono.error(new VersionMappingAlreadyExistsException(vm.getId()));
              return vm;
            })
        .defaultIfEmpty(versionMapping);
  }
}
