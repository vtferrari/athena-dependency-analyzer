package com.netshoes.athena.usecases.exceptions;

import lombok.Getter;

public class ScmApiRateLimitExceededException extends Exception {
  @Getter private final Long minutesToReset;

  public ScmApiRateLimitExceededException(Throwable cause, Long minutesToReset) {
    super("SCM API Rate limit exceeded, please try again in " + minutesToReset + " minutes", cause);
    this.minutesToReset = minutesToReset;
  }
}
