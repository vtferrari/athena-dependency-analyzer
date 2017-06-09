package com.netshoes.athena.gateways.http.jsons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SourceControlManagerRepository")
public class ScmRepositoryJson {

  @ApiModelProperty(value = "Id of repository", required = true)
  private final String id;

  @ApiModelProperty(value = "Name of repository", required = true)
  private final String name;

  @ApiModelProperty(value = "Description of repository", required = true)
  private final String description;

  @ApiModelProperty(value = "URL of repository", required = true)
  private final String url;

  @ApiModelProperty(value = "Master branch of repository", required = true)
  private final String masterBranch;

  public ScmRepositoryJson(com.netshoes.athena.domains.ScmRepository domain) {
    this.id = domain.getId();
    this.name = domain.getName();
    this.description = domain.getDescription();
    this.url = domain.getUrl().toString();
    this.masterBranch = domain.getMasterBranch();
  }
}
