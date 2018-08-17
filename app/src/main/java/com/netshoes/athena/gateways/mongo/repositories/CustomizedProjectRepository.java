package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.domains.ProjectFilter;
import com.netshoes.athena.gateways.mongo.docs.ArtifactUsageDoc;
import com.netshoes.athena.gateways.mongo.docs.ProjectDoc;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomizedProjectRepository {

  Flux<ProjectDoc> findByFilter(ProjectFilter filter, Optional<Pageable> pageable);

  Mono<Long> countByFilter(ProjectFilter filter);

  Flux<ArtifactUsageDoc> aggregateArtifactUsage(ArtifactFilter filter);

  Flux<ArtifactUsageDoc> aggregateArtifactUsage(Pageable pageable, ArtifactFilter filter);

  Mono<Long> aggregateArtifactUsageCount(ArtifactFilter filter);
}
