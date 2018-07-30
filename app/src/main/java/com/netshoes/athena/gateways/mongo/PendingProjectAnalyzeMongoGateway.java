package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import com.netshoes.athena.gateways.PendingProjectAnalyzeGateway;
import com.netshoes.athena.gateways.mongo.docs.PendingProjectAnalyzeDoc;
import com.netshoes.athena.gateways.mongo.repositories.PendingProjectAnalyzeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class PendingProjectAnalyzeMongoGateway implements PendingProjectAnalyzeGateway {

  private final PendingProjectAnalyzeRepository pendingProjectAnalyzeRepository;

  @Override
  public Mono<PendingProjectAnalyze> findById(String id) {
    return pendingProjectAnalyzeRepository.findById(id).map(PendingProjectAnalyzeDoc::toDomain);
  }

  @Override
  public Flux<PendingProjectAnalyze> findAll() {
    return pendingProjectAnalyzeRepository.findAll().map(PendingProjectAnalyzeDoc::toDomain);
  }

  @Override
  public Mono<Void> delete(String id) {
    return pendingProjectAnalyzeRepository
        .findById(id)
        .flatMap(pendingProjectAnalyzeRepository::delete)
        .doOnSuccess(voidMono -> log.trace("PendingProjectAnalyze {} deleted.", id))
        .then();
  }

  @Override
  public Mono<PendingProjectAnalyze> save(PendingProjectAnalyze pendingProjectAnalyze) {
    return Mono.just(pendingProjectAnalyze)
        .map(PendingProjectAnalyzeDoc::new)
        .flatMap(pendingProjectAnalyzeRepository::save)
        .doOnSuccess(doc -> log.trace("PendingProjectAnalyze {} saved.", doc.getId()))
        .map(PendingProjectAnalyzeDoc::toDomain);
  }
}
