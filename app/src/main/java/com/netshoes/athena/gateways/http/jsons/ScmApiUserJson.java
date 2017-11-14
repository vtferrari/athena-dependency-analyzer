package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.ScmApiUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "ScmApiUser")
public class ScmApiUserJson {
  @ApiModelProperty(value = "Login of user in SCM", required = true)
  private final String login;

  @ApiModelProperty(value = "Name of user in SCM")
  private final String name;

  @ApiModelProperty(value = "Request limit of this user toPageable SCM API", required = true)
  private final Integer requestLimit;

  @ApiModelProperty(value = "Remaining requests of this user toPageable SCM API", required = true)
  private final Integer remainingRequests;

  @ApiModelProperty(value = "Error message in case of authentication problem")
  private final String authenticationError;

  public ScmApiUserJson(ScmApiUser domain) {
    this.login = domain.getLogin();
    this.name = domain.getName();
    this.requestLimit = domain.getRequestLimit();
    this.remainingRequests = domain.getRemainingRequests();
    this.authenticationError = domain.getAuthenticationError();
  }
}
