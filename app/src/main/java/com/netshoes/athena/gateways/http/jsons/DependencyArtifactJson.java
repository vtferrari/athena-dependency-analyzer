package com.netshoes.athena.gateways.http.jsons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "DependencyArtifactDoc")
public class DependencyArtifactJson extends ArtifactJson {

  @ApiModelProperty(
      value = "Scope of artifact",
      allowableValues = "compile,import,provided,runtime,system,test,managed,undefined",
      required = true)
  private final String scope;

  public DependencyArtifactJson(com.netshoes.athena.domains.Artifact artifact) {
    super(artifact);
    if (artifact instanceof com.netshoes.athena.domains.DependencyArtifact) {
      this.scope =
          ((com.netshoes.athena.domains.DependencyArtifact) artifact).getScope().getStringValue();
    } else {
      this.scope = null;
    }
  }
}
