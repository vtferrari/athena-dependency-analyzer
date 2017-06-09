package com.netshoes.athena.domains;

import java.io.Serializable;
import lombok.Data;

@Data
public class DependencyArtifact extends Artifact implements Serializable {

  private final DependencyScope scope;

  public DependencyArtifact(
      String groupId,
      String artifactId,
      String version,
      DependencyScope scope,
      ArtifactOrigin origin) {
    super(groupId, artifactId, version, origin);
    this.scope = scope;
  }
}
