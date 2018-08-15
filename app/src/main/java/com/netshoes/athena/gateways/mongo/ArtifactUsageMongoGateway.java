package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.domains.ArtifactUsage;
import com.netshoes.athena.domains.RequestOfPage;
import com.netshoes.athena.gateways.ArtifactUsageGateway;
import com.netshoes.athena.gateways.mongo.docs.ArtifactUsageDoc;
import com.netshoes.athena.gateways.mongo.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArtifactUsageMongoGateway implements ArtifactUsageGateway {
  private final ProjectRepository projectRepository;
  private final PaginationHelper paginationHelper;

  @Override
  public Mono<ArtifactUsage> findOne(ArtifactFilter filter) {
    return projectRepository
        .aggregateArtifactUsage(filter)
        .map(ArtifactUsageDoc::toDomain)
        .singleOrEmpty();
  }

  @Override
  public Flux<ArtifactUsage> findAll(RequestOfPage requestOfPage, ArtifactFilter filter) {
    return paginationHelper
        .createRequest(requestOfPage)
        .flatMapMany(pageRequest -> projectRepository.aggregateArtifactUsage(pageRequest, filter))
        .map(ArtifactUsageDoc::toDomain);
  }

  @Override
  public Mono<Long> count(ArtifactFilter filter) {
    return projectRepository.aggregateArtifactUsageCount(filter);
  }
}
