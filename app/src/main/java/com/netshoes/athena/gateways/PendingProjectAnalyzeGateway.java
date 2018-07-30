package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PendingProjectAnalyzeGateway {

  Mono<PendingProjectAnalyze> findById(String id);

  Flux<PendingProjectAnalyze> findAll();

  Mono<Void> delete(String id);

  Mono<PendingProjectAnalyze> save(PendingProjectAnalyze pendingProjectAnalyze);
}
