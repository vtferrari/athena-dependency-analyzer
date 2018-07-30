package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.gateways.mongo.docs.ProjectDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProjectRepository extends ReactiveCrudRepository<ProjectDoc, String> {

  @Query("{}")
  Flux<ProjectDoc> findAll(Pageable pageable);

  Flux<ProjectDoc> findByNameContaining(String name, Pageable page);

  Mono<Long> countByNameContaining(String name);
}
