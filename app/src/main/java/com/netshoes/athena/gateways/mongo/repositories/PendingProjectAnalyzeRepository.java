package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.gateways.mongo.docs.PendingProjectAnalyzeDoc;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingProjectAnalyzeRepository
    extends ReactiveCrudRepository<PendingProjectAnalyzeDoc, String> {}
