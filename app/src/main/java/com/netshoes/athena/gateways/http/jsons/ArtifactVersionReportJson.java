package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.ArtifactVersionReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "ArtifactVersionReport")
public class ArtifactVersionReportJson implements Serializable {

  @ApiModelProperty(value = "Indicate if the artifact are in stable version", required = true)
  private boolean stable;

  @ApiModelProperty(value = "Stable version", required = true)
  private String stableVersion;

  @ApiModelProperty(value = "Summary", required = true)
  private String summary;

  @ApiModelProperty(value = "Alternatives ")
  private List<ArtifactVersionReportJson> alternatives;

  public ArtifactVersionReportJson(ArtifactVersionReport domain) {
    this.stable = domain.isStable();
    this.stableVersion = domain.getStableVersion();
    this.summary = domain.getSummary();
    this.alternatives =
        domain
            .getAlternatives()
            .stream()
            .map(ArtifactVersionReportJson::new)
            .collect(Collectors.toList());
  }
}
