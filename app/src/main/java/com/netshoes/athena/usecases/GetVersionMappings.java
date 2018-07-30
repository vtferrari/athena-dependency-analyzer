package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.usecases.exceptions.VersionMappingNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetVersionMappings {
  private final VersionMappingGateway versionMappingGateway;

  public Mono<VersionMapping> byId(String id) throws VersionMappingNotFoundException {
    return versionMappingGateway
        .findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new VersionMappingNotFoundException(id))));
  }

  public Flux<VersionMapping> all() {
    return versionMappingGateway.findAll();
  }
}
