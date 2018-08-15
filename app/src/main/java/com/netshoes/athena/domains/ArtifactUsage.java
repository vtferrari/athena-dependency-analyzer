package com.netshoes.athena.domains;

import java.io.Serializable;
import lombok.Getter;

public class ArtifactUsage implements Serializable {
  @Getter private final String id;
  @Getter private final String groupId;
  @Getter private final String artifactId;
  @Getter private final String version;
  @Getter private final Integer qtyProjects;

  public ArtifactUsage(String groupId, String artifactId, String version, Integer qtyProjects) {
    this.id = Artifact.generateId(groupId, artifactId, version);
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.qtyProjects = qtyProjects;
  }
}
