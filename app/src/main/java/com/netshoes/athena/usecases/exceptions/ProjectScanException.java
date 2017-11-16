package com.netshoes.athena.usecases.exceptions;

public class ProjectScanException extends Exception {

  public ProjectScanException(Throwable cause) {
    super("Could not scan dependencies", cause);
  }
}
