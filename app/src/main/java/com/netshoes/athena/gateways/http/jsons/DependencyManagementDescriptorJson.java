package com.netshoes.athena.gateways.http.jsons;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.DependencyManagementDescriptor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;

@Data
@ApiModel(value = "DependencyManagementDescriptorDoc")
public class DependencyManagementDescriptorJson {

  @ApiModelProperty(value = "Id of dependency management descriptor", required = true)
  private final String id;

  @ApiModelProperty(
    value = "Project's artifact of dependency management descriptor",
    required = true
  )
  private final ArtifactJson project;

  @ApiModelProperty(value = "Parent's artifact of dependency management descriptor")
  private final ArtifactJson parent;

  @ApiModelProperty("List of dependencies")
  private final List<DependencyArtifactJson> artifacts;

  public DependencyManagementDescriptorJson(DependencyManagementDescriptor domain) {
    final List<com.netshoes.athena.domains.Artifact> domainArtifacts = domain.getArtifacts();
    final List<DependencyArtifactJson> artifacts =
        domainArtifacts
            .stream()
            .map(a -> new DependencyArtifactJson(a))
            .collect(Collectors.toList());

    this.project = new ArtifactJson(domain.getProject());
    final Optional<Artifact> parentArtifact = domain.getParentArtifact();
    this.parent = parentArtifact.map(ArtifactJson::new).orElse(null);

    this.id = project.getId();
    this.artifacts = artifacts;
  }
}
