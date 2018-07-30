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
public class DeleteVersionMapping {
  private final VersionMappingGateway versionMappingGateway;

  public Mono<Void> byId(String id) {
    return versionMappingGateway
        .findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new VersionMappingNotFoundException(id))))
        .map(VersionMapping::getId)
        .flatMap(versionMappingGateway::delete)
        .doOnSuccess(aVoid -> log.info("VersionMapping {} deleted", id));
  }
}
