package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.domains.ArtifactUsage;
import com.netshoes.athena.domains.RequestOfPage;
import com.netshoes.athena.gateways.ArtifactUsageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetArtifactsUsage {
  private final ArtifactUsageGateway artifactUsageGateway;

  public Flux<ArtifactUsage> all(RequestOfPage requestOfPage, ArtifactFilter filter) {
    return artifactUsageGateway.findAll(requestOfPage, filter);
  }

  public Mono<Long> count(ArtifactFilter filter) {
    return artifactUsageGateway.count(filter);
  }

  public Mono<ArtifactUsage> one(ArtifactFilter filter) {
    return artifactUsageGateway.findOne(filter);
  }
}
