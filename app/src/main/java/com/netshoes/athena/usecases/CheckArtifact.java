package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckArtifact {
  private final VersionMappingGateway versionMappingGateway;

  public Optional<ArtifactVersionReport> check(final Artifact artifact) {

    final Optional<VersionMapping> opVersionMapping =
        versionMappingGateway.findByArtifact(artifact);

    Optional<ArtifactVersionReport> result;
    if (opVersionMapping.isPresent()) {
      final VersionMapping versionMapping = opVersionMapping.get();
      result =
          versionMapping
              .getPatterns()
              .stream()
              .map(pattern -> pattern.check(artifact))
              .filter(ArtifactVersionReport::isMatched)
              .findFirst();
    } else {
      result = Optional.empty();
    }
    return result;
  }
}
