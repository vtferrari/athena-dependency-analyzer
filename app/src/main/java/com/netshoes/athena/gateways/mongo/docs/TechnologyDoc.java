package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.Technology;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum TechnologyDoc {
  REDIS,
  MONGODB,
  KAFKA,
  RABBITMQ,
  HORNETQ,
  ORACLE,
  MYSQL,
  SQLSERVER,
  CASSANDRA;

  public static Set<Technology> toDomain(Set<TechnologyDoc> technologyDocs) {
    if (technologyDocs != null) {
      return technologyDocs.stream().map(TechnologyDoc::toDomain).collect(Collectors.toSet());
    } else {
      return new HashSet<>();
    }
  }

  private static Technology toDomain(TechnologyDoc tech) {
    return Technology.valueOf(tech.name());
  }
}
