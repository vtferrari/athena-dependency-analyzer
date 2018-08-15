package com.netshoes.athena.gateways.mongo.repositories.impl;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Aggregates.addFields;
import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.skip;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

import com.mongodb.client.model.Field;
import com.netshoes.athena.domains.ArtifactFilter;
import com.netshoes.athena.gateways.mongo.docs.ArtifactUsageDoc;
import com.netshoes.athena.gateways.mongo.repositories.CustomizedProjectRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomizedProjectRepositoryImpl implements CustomizedProjectRepository {
  private final ReactiveMongoTemplate mongoTemplate;

  private List<Bson> buildAggregateArtifactUsageStages(ArtifactFilter filter) {
    final List<Bson> stages = new LinkedList<>();

    stages.add(unwind("$descriptors"));
    stages.add(unwind("$descriptors.artifacts"));

    final Optional<Bson> criteriaOp = buildCriteria(filter);
    criteriaOp.ifPresent(bson -> stages.add(match(bson)));

    stages.add(
        group(
            new Document()
                .append("groupId", "$descriptors.artifacts.groupId")
                .append("artifactId", "$descriptors.artifacts.artifactId")
                .append("version", "$descriptors.artifacts.version"),
            addToSet(
                "projects",
                new Document()
                    .append("projectId", "$_id")
                    .append("projectName", "$name")
                    .append("repository", "$scmRepository"))));
    stages.add(addFields(new Field("qtyProjects", new Document("$size", "$projects"))));

    return stages;
  }

  @Override
  public Flux<ArtifactUsageDoc> aggregateArtifactUsage(ArtifactFilter filter) {
    return aggregateArtifactUsage(null, filter);
  }

  @Override
  public Flux<ArtifactUsageDoc> aggregateArtifactUsage(Pageable pageable, ArtifactFilter filter) {
    return mongoTemplate
        .execute(
            com.netshoes.athena.gateways.mongo.docs.ProjectDoc.class,
            collection -> {
              final List<Bson> stages = buildAggregateArtifactUsageStages(filter);
              stages.add(sort(orderBy(descending("qtyProjects"))));

              if (pageable != null) {
                stages.add(skip(pageable.getPageSize() * (pageable.getPageNumber())));
                stages.add(limit(pageable.getPageSize()));
              }
              return collection.aggregate(stages);
            })
        .map(this::toArtifactUsageDoc);
  }

  @Override
  public Mono<Long> aggregateArtifactUsageCount(ArtifactFilter filter) {
    return mongoTemplate
        .execute(
            com.netshoes.athena.gateways.mongo.docs.ProjectDoc.class,
            collection -> {
              final List<Bson> stages = buildAggregateArtifactUsageStages(filter);
              stages.add(count());
              return collection.aggregate(stages);
            })
        .singleOrEmpty()
        .map(document -> Long.valueOf(document.getInteger("count")))
        .defaultIfEmpty(0L);
  }

  private ArtifactUsageDoc toArtifactUsageDoc(Document result) {
    final Document id = result.get("_id", Document.class);
    final String groupId = id.get("groupId", String.class);
    final String artifactId = id.get("artifactId", String.class);
    final String version = id.get("version", String.class);
    final Integer qtyProjects = result.getInteger("qtyProjects");

    return new ArtifactUsageDoc(groupId, artifactId, version, qtyProjects);
  }

  private Optional<Bson> buildCriteria(ArtifactFilter filter) {
    Bson criteria = null;
    if (filter != null && filter.isPresent()) {
      final List<Bson> filters = new LinkedList<>();

      final Optional<String> groupIdOp = filter.getGroupId();
      groupIdOp.ifPresent(s -> filters.add(eq("descriptors.artifacts.groupId", s)));

      final Optional<String> artifactIdOp = filter.getArtifactId();
      artifactIdOp.ifPresent(s -> filters.add(eq("descriptors.artifacts.artifactId", s)));

      final Optional<String> versionOp = filter.getVersion();
      versionOp.ifPresent(s -> filters.add(eq("descriptors.artifacts.version", s)));
      criteria = and(filters);
    }
    return Optional.ofNullable(criteria);
  }
}
