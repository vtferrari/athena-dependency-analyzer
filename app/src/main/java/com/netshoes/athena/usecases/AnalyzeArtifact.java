package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AnalyzeArtifact {
  private final VersionMappingGateway versionMappingGateway;

  public Optional<ArtifactVersionReport> execute(final Artifact artifact) {

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
      log.trace("Artifact {} analyzed. Result {}", artifact, result);
    } else {
      result = Optional.empty();
      log.trace("Artifact {} analyzed. Result none", artifact);
    }
    return result;
  }
}
