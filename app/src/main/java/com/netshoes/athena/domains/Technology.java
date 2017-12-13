package com.netshoes.athena.domains;

import java.util.HashSet;
import java.util.Set;

public enum Technology {
  REDIS,
  MONGODB,
  KAFKA,
  RABBITMQ,
  HORNETQ,
  ORACLE,
  MYSQL,
  SQLSERVER,
  CASSANDRA;

  public static Set<Technology> discover(Artifact artifact) {
    final String artifactId = artifact.getArtifactId();
    final Set<Technology> technologies = new HashSet<>();
    if (artifactId.contains("kafka")) {
      technologies.add(KAFKA);
    }
    if (artifactId.contains("ojdbc")) {
      technologies.add(ORACLE);
    }
    if (artifactId.contains("mongo")) {
      technologies.add(MONGODB);
    }
    if (artifactId.contains("rabbit") || artifactId.contains("amqp")) {
      technologies.add(RABBITMQ);
    }
    if (artifactId.contains("redis") || artifactId.contains("jedis")) {
      technologies.add(REDIS);
    }
    if (artifactId.contains("hornetq")) {
      technologies.add(HORNETQ);
    }
    if (artifactId.contains("mssql")) {
      technologies.add(SQLSERVER);
    }
    if (artifactId.contains("mysql")) {
      technologies.add(MYSQL);
    }
    if (artifactId.contains("cassandra")) {
      technologies.add(CASSANDRA);
    }
    return technologies;
  }
}
