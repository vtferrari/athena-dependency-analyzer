package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import java.io.Serializable;
import java.util.List;
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
        domainArtifacts
            .stream()
            .map(DependencyArtifactDoc::new)
            .collect(Collectors.toList());

    this.project = new ArtifactDoc(domain.getProject());
    this.artifacts = artifacts;
  }
}
