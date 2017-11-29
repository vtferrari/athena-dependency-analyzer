package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.gateways.mongo.docs.VersionMappingDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionMappingRepository extends MongoRepository<VersionMappingDoc, String> {}
