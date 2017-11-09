package com.netshoes.athena.usecases.exceptions;

public class DependencyCollectException extends Exception {

  public DependencyCollectException(Throwable cause) {
    super("Could not analyse dependencies", cause);
  }
}
