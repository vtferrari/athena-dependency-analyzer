package com.netshoes.athena.gateways.http.jsons;

import com.netshoes.athena.domains.Artifact;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ArtifactDoc")
public class ArtifactJson {

  @ApiModelProperty(value = "Group id of artifact", required = true)
  private final String groupId;

  @ApiModelProperty(value = "ArtifactDoc id", required = true)
  private final String artifactId;

  @ApiModelProperty(value = "Version of artifact, could be 'managed'.", required = true)
  private final String version;

  @ApiModelProperty(
    value = "Origin of artifact",
    allowableValues = "PROJECT,PARENT,DEPENDENCIES,DEPENDENCIES_MANAGEMENT",
    required = true
  )
  private final ArtifactOrigin origin;

  public ArtifactJson(Artifact artifact) {
    this.groupId = artifact.getGroupId();
    this.artifactId = artifact.getArtifactId();
    this.version = artifact.getVersion();
    this.origin = ArtifactOrigin.valueOf(artifact.getOrigin().name());
  }
}
