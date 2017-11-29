package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import com.netshoes.athena.gateways.mongo.docs.VersionMappingDoc;
import com.netshoes.athena.gateways.mongo.repositories.VersionMappingRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VersionMappingMongoGateway implements VersionMappingGateway {
  private VersionMappingRepository versionMappingRepository;

  @Override
  public Optional<VersionMapping> findById(String id) {
    final VersionMappingDoc versionMapping = versionMappingRepository.findOne(id);
    return Optional.ofNullable(versionMapping).map(VersionMappingDoc::toDomain);
  }

  @Override
  public Optional<VersionMapping> findByArtifact(Artifact artifact) {
    final String id = VersionMapping.generateId(artifact.getGroupId(), artifact.getArtifactId());
    final VersionMappingDoc versionMapping = versionMappingRepository.findOne(id);
    return Optional.ofNullable(versionMapping).map(VersionMappingDoc::toDomain);
  }

  @Override
  public List<VersionMapping> findAll() {
    final List<VersionMappingDoc> list = versionMappingRepository.findAll();
    return list.stream().map(VersionMappingDoc::toDomain).collect(Collectors.toList());
  }

  @Override
  public VersionMapping save(VersionMapping versionMapping) {
    final VersionMappingDoc versionMappingDoc = new VersionMappingDoc(versionMapping);
    final VersionMappingDoc saved = versionMappingRepository.save(versionMappingDoc);
    return saved.toDomain();
  }

  @Override
  public void delete(String id) {
    versionMappingRepository.delete(id);
  }
}
