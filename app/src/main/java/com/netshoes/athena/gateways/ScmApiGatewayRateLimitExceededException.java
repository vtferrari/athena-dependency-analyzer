package com.netshoes.athena.gateways;

import lombok.Getter;

public class ScmApiGatewayRateLimitExceededException extends Exception {
  @Getter private final Long minutesToReset;

  public ScmApiGatewayRateLimitExceededException(Throwable cause, Long minutesToReset) {
    super("SCM API Rate limit exceeded, try again in " + minutesToReset + " minutes", cause);
    this.minutesToReset = minutesToReset;
  }
}
