package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.VersionMapping;
import java.util.List;
import java.util.Optional;

public interface VersionMappingGateway {
  Optional<VersionMapping> findById(String id);

  List<VersionMapping> findAll();

  Optional<VersionMapping> findByArtifact(Artifact artifact);

  VersionMapping save(VersionMapping versionMapping);

  void delete(String id);
}
