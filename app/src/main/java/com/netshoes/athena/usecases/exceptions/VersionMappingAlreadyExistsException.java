package com.netshoes.athena.usecases.exceptions;

public class VersionMappingAlreadyExistsException extends DomainAlreadyExistsException {

  public VersionMappingAlreadyExistsException(String id) {
    super("Version mapping already exists", id);
  }
}
