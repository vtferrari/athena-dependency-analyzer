package com.netshoes.athena.domains;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import lombok.Data;
import org.springframework.util.Base64Utils;

@Data
public class Artifact implements Serializable {

  private final String id;
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final ArtifactOrigin origin;

  public Artifact(String groupId, String artifactId, String version, ArtifactOrigin origin) {
    this.id = generateId(groupId, artifactId, version);
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.origin = origin;
  }

  private String generateId(String groupId, String artifactId, String version) {
    final String baseId = MessageFormat.format("{0}:{1}:{2}", groupId, artifactId, version);
    String generateId;
    try {
      generateId = Base64Utils.encodeToString(baseId.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return generateId;
  }
}
