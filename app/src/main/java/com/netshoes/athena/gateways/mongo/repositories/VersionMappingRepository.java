package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.gateways.mongo.docs.VersionMappingDoc;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionMappingRepository
    extends ReactiveCrudRepository<VersionMappingDoc, String> {}
