package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.Artifact;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtifactDoc {

  private String groupId;
  private String artifactId;
  private String version;
  private ArtifactOriginDoc origin;

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
  }
}
