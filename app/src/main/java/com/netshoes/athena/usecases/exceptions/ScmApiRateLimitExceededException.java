package com.netshoes.athena.usecases.exceptions;

import com.netshoes.athena.gateways.ScmApiGatewayRateLimitExceededException;
import lombok.Getter;

public class ScmApiRateLimitExceededException extends RuntimeException {
  @Getter private final Long minutesToReset;

  public ScmApiRateLimitExceededException(ScmApiGatewayRateLimitExceededException cause) {
    this(cause, cause.getMinutesToReset());
  }

  public ScmApiRateLimitExceededException(Throwable cause, Long minutesToReset) {
    super("SCM API Rate limit exceeded, please try again in " + minutesToReset + " minutes", cause);
    this.minutesToReset = minutesToReset;
  }
}
