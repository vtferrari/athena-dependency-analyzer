package com.netshoes.athena.domains;

import java.io.Serializable;
import java.text.MessageFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
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

  @Override
  public String toString() {
    return MessageFormat.format("{0}:({1})", super.toString(), scope);
  }
}
