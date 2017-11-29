package com.netshoes.athena.domains;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Comparator;
import lombok.Data;
import org.springframework.util.Base64Utils;

@Data
public class Artifact implements Serializable, Comparable {

  private final String id;
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final ArtifactOrigin origin;

  public Artifact(String groupId, String artifactId, String version, ArtifactOrigin origin) {
    this.id = generateId(groupId, artifactId, version);
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.origin = origin;
  }

  public static Artifact ofParent(String groupId, String artifactId, String version) {
    return new Artifact(groupId, artifactId, version, ArtifactOrigin.PARENT);
  }

  public static Artifact ofProject(String groupId, String artifactId, String version) {
    return new Artifact(groupId, artifactId, version, ArtifactOrigin.PROJECT);
  }

  public static Artifact ofProject(
      String groupId, String artifactId, String version, Artifact parent) {
    final String resolvedGroupId = groupId == null ? parent.getGroupId() : groupId;
    final String resolvedVersion = version == null ? parent.getVersion() : version;

    return new Artifact(resolvedGroupId, artifactId, resolvedVersion, ArtifactOrigin.PROJECT);
  }

  private String generateId(String groupId, String artifactId, String version) {

    final String baseId = MessageFormat.format("{0}:{1}:{2}", groupId, artifactId, version);
    String generateId;
    try {
      generateId = Base64Utils.encodeToUrlSafeString(baseId.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return generateId;
  }

  @Override
  public String toString() {
    return MessageFormat.format("{0}:{1}:{2}", groupId, artifactId, version);
  }

  @Override
  public int compareTo(Object o) {
    return Comparator.comparing(Artifact::getGroupId)
        .thenComparing(Artifact::getArtifactId)
        .thenComparing(Artifact::getVersion, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(Artifact::getOrigin)
        .compare(this, (Artifact) o);
  }
}
