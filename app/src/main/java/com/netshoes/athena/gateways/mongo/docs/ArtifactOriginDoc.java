package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.ArtifactOrigin;

public enum ArtifactOriginDoc {
  PROJECT,
  PARENT,
  DEPENDENCIES,
  DEPENDENCIES_MANAGEMENT;

  ArtifactOrigin toDomain() {
    ArtifactOrigin artifactOrigin = null;
    switch (this) {
      case PARENT:
        artifactOrigin = ArtifactOrigin.PARENT;
        break;
      case PROJECT:
        artifactOrigin = ArtifactOrigin.PROJECT;
        break;
      case DEPENDENCIES:
        artifactOrigin = ArtifactOrigin.DEPENDENCIES;
        break;
      case DEPENDENCIES_MANAGEMENT:
        artifactOrigin = ArtifactOrigin.DEPENDENCIES_MANAGEMENT;
        break;
    }
    return artifactOrigin;
  }
}
