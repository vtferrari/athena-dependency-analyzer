package com.netshoes.athena.usecases.exceptions;

public class DescriptorNotFoundException extends DomainNotFoundException {
  public DescriptorNotFoundException(String descriptorId) {
    super("Descriptor not found", descriptorId);
  }
}
