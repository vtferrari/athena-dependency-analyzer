package com.netshoes.athena.usecases.exceptions;

public class VersionMappingNotFoundException extends DomainNotFoundException {

  public VersionMappingNotFoundException(String id) {
    super("Version mapping not found", id);
  }
}
