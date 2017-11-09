package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.DependencyArtifact;
import com.netshoes.athena.domains.DependencyScope;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DependencyArtifactDoc extends ArtifactDoc {

  private String scope;

  public DependencyArtifactDoc(Artifact artifact) {
    super(artifact);
    if (artifact instanceof com.netshoes.athena.domains.DependencyArtifact) {
      this.scope =
          ((com.netshoes.athena.domains.DependencyArtifact) artifact).getScope().getStringValue();
    }
  }

  public DependencyArtifact toDomain() {
    return new DependencyArtifact(
        getGroupId(),
        getArtifactId(),
        getVersion(),
        DependencyScope.fromString(scope),
        getOrigin().toDomain());
  }
}
