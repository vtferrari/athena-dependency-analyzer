package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.usecases.exceptions.VersionMappingNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetVersionMappings {
  private final VersionMappingGateway versionMappingGateway;

  public VersionMapping byId(String id) throws VersionMappingNotFoundException {
    final Optional<VersionMapping> opVersionMapping = versionMappingGateway.findById(id);

    if (!opVersionMapping.isPresent()) {
      throw new VersionMappingNotFoundException(id);
    }

    return opVersionMapping.get();
  }

  public List<VersionMapping> all() {
    return versionMappingGateway.findAll();
  }
}
