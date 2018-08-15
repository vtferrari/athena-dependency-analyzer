package com.netshoes.athena.domains;

import java.io.Serializable;
import java.util.Optional;
import lombok.Getter;

public class ArtifactFilter implements Serializable {
  @Getter private final Optional<String> groupId;
  @Getter private final Optional<String> artifactId;
  @Getter private final Optional<String> version;

  public ArtifactFilter(String groupId, String artifactId, String version) {
    this.groupId = Optional.ofNullable(groupId);
    this.artifactId = Optional.ofNullable(artifactId);
    this.version = Optional.ofNullable(version);
  }

  public boolean isPresent() {
    return groupId.isPresent() || artifactId.isPresent() || artifactId.isPresent();
  }
}
