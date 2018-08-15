package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.gateways.mongo.docs.ArtifactUsageDoc;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomizedProjectRepository {
  Flux<ArtifactUsageDoc> aggregateArtifactUsage(ArtifactFilter filter);

  Flux<ArtifactUsageDoc> aggregateArtifactUsage(Pageable pageable, ArtifactFilter filter);

  Mono<Long> aggregateArtifactUsageCount(ArtifactFilter filter);
}
