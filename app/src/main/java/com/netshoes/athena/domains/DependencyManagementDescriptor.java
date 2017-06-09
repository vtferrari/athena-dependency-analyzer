package com.netshoes.athena.domains;

import java.util.List;

public interface DependencyManagementDescriptor {

  Artifact getParentArtifact();

  Artifact getProject();

  List<DependencyArtifact> getDependencyArtifacts();

  List<DependencyArtifact> getDependencyManagementArtifacts();

  List<Artifact> getArtifacts();
}
