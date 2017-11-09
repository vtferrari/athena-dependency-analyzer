package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.DependencyArtifact;
import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.MavenDependencyManagementDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DependencyManagementDescriptorDoc implements Serializable {
  private ArtifactDoc project;
  private List<DependencyArtifactDoc> artifacts;

  public DependencyManagementDescriptorDoc(DependencyManagementDescriptor domain) {

    final List<com.netshoes.athena.domains.Artifact> domainArtifacts = domain.getArtifacts();
    final List<DependencyArtifactDoc> artifacts =
        domainArtifacts.stream().map(DependencyArtifactDoc::new).collect(Collectors.toList());

    this.project = new ArtifactDoc(domain.getProject());
    this.artifacts = artifacts;
  }

  public DependencyManagementDescriptor toDomain() {
    final Artifact projectDomain = project.toDomain();
    final MavenDependencyManagementDescriptor domain =
        new MavenDependencyManagementDescriptor(projectDomain);

    Optional<Artifact> parentArtifact = Optional.empty();
    final List<DependencyArtifact> dependencyArtifacts = new ArrayList<>();
    final List<DependencyArtifact> dependencyManagementArtifacts = new ArrayList<>();

    for (DependencyArtifactDoc artifactDoc : artifacts) {
      switch (artifactDoc.getOrigin()) {
        case PARENT:
          parentArtifact = Optional.of(artifactDoc.toDomain());
          break;
        case DEPENDENCIES:
          dependencyArtifacts.add(artifactDoc.toDomain());
          break;
        case DEPENDENCIES_MANAGEMENT:
          dependencyManagementArtifacts.add(artifactDoc.toDomain());
          break;
      }
    }

    domain.setParentArtifact(parentArtifact);
    domain.setDependencyArtifacts(dependencyArtifacts);
    domain.setDependencyManagementArtifacts(dependencyManagementArtifacts);

    return domain;
  }
}
