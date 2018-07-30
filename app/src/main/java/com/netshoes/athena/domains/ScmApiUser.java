package com.netshoes.athena.domains;

import lombok.Data;

@Data
public class ScmApiUser {
  private final String login;
  private final String name;
  private final Integer requestLimit;
  private final Integer remainingRequests;
  private final String authenticationError;

  public static ScmApiUser ofAuthenticatedUser(
      String login, String name, Integer requestLimit, Integer remainingRequests) {
    return new ScmApiUser(login, name, requestLimit, remainingRequests);
  }

  public static ScmApiUser ofInvalidUser(
      String login,
      Exception authenticationError,
      Integer requestLimit,
      Integer remainingRequests) {
    return new ScmApiUser(login, authenticationError, requestLimit, remainingRequests);
  }

  private ScmApiUser(String login, Integer requestLimit, Integer remainingRequests) {
    this.login = login;
    this.name = null;
    this.requestLimit = requestLimit;
    this.remainingRequests = remainingRequests;
    this.authenticationError = null;
  }

  public ScmApiUser(String login, String name, Integer requestLimit, Integer remainingRequests) {
    this.login = login;
    this.name = name;
    this.requestLimit = requestLimit;
    this.remainingRequests = remainingRequests;
    this.authenticationError = null;
  }

  private ScmApiUser(
      String login,
      Exception authenticationError,
      Integer requestLimit,
      Integer remainingRequests) {
    this.login = login;
    this.name = null;
    this.requestLimit = requestLimit;
    this.remainingRequests = remainingRequests;
    this.authenticationError = authenticationError.getMessage();
  }
}
