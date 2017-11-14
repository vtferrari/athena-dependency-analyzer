package com.netshoes.athena.usecases.exceptions;

public class DescriptorNotFoundException extends DomainNotFoundException {
  private final String projectId;

  public DescriptorNotFoundException(String projectId) {
    super("Descriptor not found");
    this.projectId = projectId;
  }
}
