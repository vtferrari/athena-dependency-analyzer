package com.netshoes.athena.usecases;

public class RequestCollectProjectException extends Exception {

  public RequestCollectProjectException(Throwable cause) {
    super("Could not request project dependency analysis", cause);
  }
}
