package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Optional;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Artifact")
public class ArtifactJson {

  @ApiModelProperty(value = "Id of artifact", required = true)
  private final String id;

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

  @ApiModelProperty(value = "Report about version of this artifact")
  private final ArtifactVersionReportJson report;

  public ArtifactJson(Artifact domain) {
    this.id = domain.getId();
    this.groupId = domain.getGroupId();
    this.artifactId = domain.getArtifactId();
    this.version = domain.getVersion();
    this.origin = ArtifactOrigin.valueOf(domain.getOrigin().name());

    final Optional<ArtifactVersionReport> opReport = domain.getReport();
    this.report = opReport.map(ArtifactVersionReportJson::new).orElse(null);
  }
}
