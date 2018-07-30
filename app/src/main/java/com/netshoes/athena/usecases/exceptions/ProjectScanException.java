package com.netshoes.athena.usecases.exceptions;

public class ProjectScanException extends RuntimeException {

  public ProjectScanException(Throwable cause) {
    super("Could not scan dependencies", cause);
  }
}
