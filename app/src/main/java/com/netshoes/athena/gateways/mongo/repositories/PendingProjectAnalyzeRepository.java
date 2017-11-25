package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.gateways.mongo.docs.PendingProjectAnalyzeDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingProjectAnalyzeRepository
    extends MongoRepository<PendingProjectAnalyzeDoc, String>,
        PagingAndSortingRepository<PendingProjectAnalyzeDoc, String> {}
