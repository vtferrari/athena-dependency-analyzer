package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.Technology;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtifactDoc {

  private String groupId;
  private String artifactId;
  private String version;
  private Set<TechnologyDoc> relatedTechnologies;
  private ArtifactOriginDoc origin;
  private ArtifactVersionReportDoc report;

  public ArtifactDoc(Artifact artifact) {
    this.groupId = artifact.getGroupId();
    this.artifactId = artifact.getArtifactId();
    final String version = artifact.getVersion();
    if (version == null) {
      this.version = "UNDEFINED";
    } else {
      this.version = version;
    }
    this.origin = ArtifactOriginDoc.valueOf(artifact.getOrigin().name());
    final Set<Technology> relatedTechnologies = artifact.getRelatedTechnologies();
    if (relatedTechnologies != null) {
      this.relatedTechnologies =
          relatedTechnologies
              .stream()
              .map(technology -> TechnologyDoc.valueOf(technology.name()))
              .collect(Collectors.toSet());
    } else {
      this.relatedTechnologies = new HashSet<>();
    }

    final Optional<ArtifactVersionReport> opReport = artifact.getReport();
    opReport.ifPresent(report -> this.report = new ArtifactVersionReportDoc(report));
  }

  public Artifact toDomain() {
    final Artifact domain = new Artifact(groupId, artifactId, version, origin.toDomain());
    return convertToDomain(this, domain);
  }

  protected static Artifact convertToDomain(ArtifactDoc artifactDoc, Artifact domain) {
    domain.addRelatedTechnologies(TechnologyDoc.toDomain(artifactDoc.relatedTechnologies));
    final ArtifactVersionReportDoc report = artifactDoc.report;
    if (report != null) {
      domain.setReport(report.toDomain());
    }
    return domain;
  }
}
