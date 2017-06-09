package com.netshoes.athena.domains;

import java.io.Serializable;
import lombok.Data;

@Data
public class Artifact implements Serializable {

  private final String groupId;
  private final String artifactId;
  private final String version;
  private final ArtifactOrigin origin;

  public Artifact(String groupId, String artifactId, String version, ArtifactOrigin origin) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.origin = origin;
  }
}
