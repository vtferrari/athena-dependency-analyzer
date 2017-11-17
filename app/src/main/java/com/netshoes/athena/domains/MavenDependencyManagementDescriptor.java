package com.netshoes.athena.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "project.id")
public class MavenDependencyManagementDescriptor implements DependencyManagementDescriptor {

  private final Artifact project;
  private final String dependencyDescriptorId;
  private Optional<Artifact> parentArtifact;
  private List<DependencyArtifact> dependencyArtifacts;
  private List<DependencyArtifact> dependencyManagementArtifacts;

  public MavenDependencyManagementDescriptor(Artifact project) {
    this.project = project;
    this.dependencyDescriptorId = project.getId();
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

  @Override
  public int compareTo(Object o) {
    final MavenDependencyManagementDescriptor other = (MavenDependencyManagementDescriptor) o;
    final Optional<Artifact> opParentArtifact = parentArtifact;
    final Optional<Artifact> opParentArtifactOther = other.parentArtifact;
    if (opParentArtifact.isPresent() && opParentArtifactOther.isPresent()) {
      return project.compareTo(other.project);
    } else if (opParentArtifact.isPresent()) {
      return -1;
    } else {
      return 1;
    }
  }
}
