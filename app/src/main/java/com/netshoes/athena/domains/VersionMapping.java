package com.netshoes.athena.domains;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Base64Utils;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class VersionMapping implements Serializable {
  private final String id;
  private final String groupId;
  private final String artifactId;
  private final List<VersionPattern> patterns;

  public VersionMapping(String groupId, String artifactId) {
    this.id = generateId(groupId, artifactId);
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.patterns = new ArrayList<>();
  }

  public static String generateId(String groupId, String artifactId) {
    final String baseId = MessageFormat.format("{0}:{1}", groupId, artifactId);
    String generateId;
    generateId = Base64Utils.encodeToUrlSafeString(baseId.getBytes(StandardCharsets.UTF_8));
    return generateId;
  }

  public VersionMapping addPattern(VersionPattern versionPattern) {
    patterns.add(versionPattern);
    return this;
  }
}
