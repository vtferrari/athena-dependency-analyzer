package com.netshoes.athena.domains;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Base64Utils;

public class Artifact implements Serializable, Comparable {

  @Getter private final String id;
  @Getter private final String groupId;
  @Getter private final String artifactId;
  @Getter private final String version;
  @Getter private final ArtifactOrigin origin;
  @Getter private final Set<Technology> relatedTechnologies;
  @Setter @Getter private Optional<ArtifactVersionReport> report;
  @Setter @Getter private boolean modified;

  public Artifact(String groupId, String artifactId, String version, ArtifactOrigin origin) {
    this.id = generateId(groupId, artifactId, version);
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.origin = origin;
    this.report = Optional.empty();
    this.relatedTechnologies = new HashSet<>();
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

  private static String generateId(String groupId, String artifactId, String version) {

    final String baseId = MessageFormat.format("{0}:{1}:{2}", groupId, artifactId, version);
    String generateId;
    try {
      generateId = Base64Utils.encodeToUrlSafeString(baseId.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return generateId;
  }

  public Artifact setReport(ArtifactVersionReport report) {
    if (!this.report.isPresent() && report != null || this.report.equals(report)) {
      this.report = Optional.of(report);
      modified = true;
    }
    return this;
  }

  public Artifact addRelatedTechnologies(Set<Technology> technologies) {
    if (!relatedTechnologies.equals(technologies)) {
      relatedTechnologies.addAll(technologies);
      modified = true;
    }
    return this;
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
