package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.ScmApiUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "ScmApiUser")
public class ScmApiUserJson {
  private final String login;
  private final String name;
  private final Integer requestLimit;
  private final Integer remainingRequests;
  private final String authenticationError;

  public ScmApiUserJson(ScmApiUser domain) {
    this.login = domain.getLogin();
    this.name = domain.getName();
    this.requestLimit = domain.getRequestLimit();
    this.remainingRequests = domain.getRemainingRequests();
    this.authenticationError = domain.getAuthenticationError();
  }
}
