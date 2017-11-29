package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.usecases.exceptions.VersionMappingAlreadyExistsException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CreateVersionMapping {
  private final VersionMappingGateway versionMappingGateway;

  public VersionMapping execute(VersionMapping versionMapping)
      throws VersionMappingAlreadyExistsException {
    final String versionMappingId = versionMapping.getId();
    final Optional<VersionMapping> opVersionMapping =
        versionMappingGateway.findById(versionMappingId);

    if (opVersionMapping.isPresent()) {
      throw new VersionMappingAlreadyExistsException(versionMappingId);
    }

    final VersionMapping saved = versionMappingGateway.save(versionMapping);
    log.info("VersionMapping {}:{} saved.", saved.getGroupId(), saved.getArtifactId());
    return saved;
  }
}
