package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.ArtifactUsage;
import lombok.Data;

@Data
public class ArtifactUsageDoc {
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final Integer qtyProjects;

  public ArtifactUsageDoc(String groupId, String artifactId, String version, Integer qtyProjects) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    if (version == null) {
      this.version = "UNDEFINED";
    } else {
      this.version = version;
    }
    this.qtyProjects = qtyProjects;
  }

  public ArtifactUsage toDomain() {
    return new ArtifactUsage(groupId, artifactId, version, qtyProjects);
  }
}
