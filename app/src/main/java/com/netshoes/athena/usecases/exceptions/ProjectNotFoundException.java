package com.netshoes.athena.usecases.exceptions;

public class ProjectNotFoundException extends DomainNotFoundException {
  public ProjectNotFoundException(String projectId) {
    super("Project not found", projectId);
  }
}
