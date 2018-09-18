package com.netshoes.athena.domains;

import java.io.Serializable;
import java.util.Optional;
import lombok.Getter;

public class ArtifactFilter implements Serializable {
  @Getter private final Optional<String> groupId;
  @Getter private final Optional<String> artifactId;
  @Getter private final Optional<String> version;

  public ArtifactFilter(String groupId, String artifactId, String version) {
    this.groupId = Optional.of(groupId);
    this.artifactId = Optional.of(artifactId);
    this.version = Optional.of(version);
  }

  public ArtifactFilter(
      Optional<String> groupId, Optional<String> artifactId, Optional<String> version) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  public boolean isPresent() {
    return groupId.isPresent() || artifactId.isPresent();
  }
}
