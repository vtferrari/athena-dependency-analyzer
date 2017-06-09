package com.netshoes.athena.domains;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class MavenDependencyManagementDescriptor implements DependencyManagementDescriptor {

  private final Artifact project;
  private Artifact parentArtifact;
  private List<DependencyArtifact> dependencyArtifacts;
  private List<DependencyArtifact> dependencyManagementArtifacts;

  public List<Artifact> getArtifacts() {
    final List<Artifact> list = new ArrayList<>();

    if (parentArtifact != null) {
      list.add(parentArtifact);
    }
    if (dependencyArtifacts != null) {
      list.addAll(dependencyArtifacts);
    }
    if (dependencyManagementArtifacts != null) {
      list.addAll(dependencyManagementArtifacts);
    }

    return list;
  }
}
