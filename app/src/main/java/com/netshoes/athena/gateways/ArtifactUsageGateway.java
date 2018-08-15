package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.domains.ArtifactUsage;
import com.netshoes.athena.domains.RequestOfPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArtifactUsageGateway {
  Mono<ArtifactUsage> findOne(ArtifactFilter filter);

  Flux<ArtifactUsage> findAll(RequestOfPage requestOfPage, ArtifactFilter filter);

  Mono<Long> count(ArtifactFilter filter);
}
