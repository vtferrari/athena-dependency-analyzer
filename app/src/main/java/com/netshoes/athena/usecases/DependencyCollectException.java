package com.netshoes.athena.usecases;

public class DependencyCollectException extends Exception {

  public DependencyCollectException(Throwable cause) {
    super("Could not analyse dependencies", cause);
  }
}
