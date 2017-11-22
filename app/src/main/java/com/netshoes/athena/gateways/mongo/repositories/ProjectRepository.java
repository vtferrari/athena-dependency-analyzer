package com.netshoes.athena.gateways.mongo.repositories;

import com.netshoes.athena.gateways.mongo.docs.ProjectDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository
    extends MongoRepository<ProjectDoc, String>, PagingAndSortingRepository<ProjectDoc, String> {

  Page<ProjectDoc> findByNameContaining(String name, Pageable page);
}
