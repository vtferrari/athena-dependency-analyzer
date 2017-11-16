package com.netshoes.athena.usecases.exceptions;

public class RequestScanException extends Exception {

  public RequestScanException(Throwable cause) {
    super("Could not request project dependency analysis", cause);
  }
}
