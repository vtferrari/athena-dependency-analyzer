package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtifactDoc {

  private String groupId;
  private String artifactId;
  private String version;
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

    final Optional<ArtifactVersionReport> opReport = artifact.getReport();
    opReport.ifPresent(report -> this.report = new ArtifactVersionReportDoc(report));
  }

  public Artifact toDomain() {
    final Artifact artifact = new Artifact(groupId, artifactId, version, origin.toDomain());
    if (report != null) {
      artifact.setReport(report.toDomain());
    }
    return artifact;
  }
}
