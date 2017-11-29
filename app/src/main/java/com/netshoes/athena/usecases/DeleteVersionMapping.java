package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.usecases.exceptions.VersionMappingNotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DeleteVersionMapping {
  private final VersionMappingGateway versionMappingGateway;

  public void byId(String id) throws VersionMappingNotFoundException {
    final Optional<VersionMapping> opVersionMapping = versionMappingGateway.findById(id);

    if (!opVersionMapping.isPresent()) {
      throw new VersionMappingNotFoundException(id);
    }

    versionMappingGateway.delete(id);
    log.info("VersionMapping {} deleted", id);
  }
}
