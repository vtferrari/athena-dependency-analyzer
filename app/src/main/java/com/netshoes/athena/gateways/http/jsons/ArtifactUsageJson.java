package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.ArtifactUsage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "ArtifactUsage")
public class ArtifactUsageJson {

  @ApiModelProperty(value = "Id of artifact", required = true)
  private final String id;

  @ApiModelProperty(value = "Group id of artifact", required = true)
  private final String groupId;

  @ApiModelProperty(value = "ArtifactDoc id", required = true)
  private final String artifactId;

  @ApiModelProperty(value = "Version of artifact, could be 'managed'.", required = true)
  private final String version;

  @ApiModelProperty(value = "Quantity of projects", required = true)
  private final Integer qtyProjects;

  public ArtifactUsageJson(ArtifactUsage domain) {
    this.id = domain.getId();
    this.groupId = domain.getGroupId();
    this.artifactId = domain.getArtifactId();
    this.version = domain.getVersion();
    this.qtyProjects = domain.getQtyProjects();
  }
}
