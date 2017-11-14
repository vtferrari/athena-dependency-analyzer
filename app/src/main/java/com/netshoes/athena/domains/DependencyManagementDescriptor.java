package com.netshoes.athena.domains;

import java.util.List;
import java.util.Optional;

public interface DependencyManagementDescriptor {

  String getDependencyDescriptorId();

  Optional<Artifact> getParentArtifact();

  Artifact getProject();

  List<DependencyArtifact> getDependencyArtifacts();

  List<DependencyArtifact> getDependencyManagementArtifacts();

  List<Artifact> getArtifacts();
}
