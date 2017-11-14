package com.netshoes.athena.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;

@Data
public class MavenDependencyManagementDescriptor implements DependencyManagementDescriptor {

  private final Artifact project;
  private Optional<Artifact> parentArtifact;
  private List<DependencyArtifact> dependencyArtifacts;
  private List<DependencyArtifact> dependencyManagementArtifacts;

  @Override
  public String getDependencyDescriptorId() {
    return project.getId();
  }

  public List<Artifact> getArtifacts() {
    final List<Artifact> list = new ArrayList<>();

    if (parentArtifact.isPresent()) {
      list.add(parentArtifact.get());
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
