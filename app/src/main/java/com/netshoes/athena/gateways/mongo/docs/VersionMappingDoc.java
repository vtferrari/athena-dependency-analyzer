package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.VersionMapping;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Document(collection = "versionMapping")
@TypeAlias("versionMapping")
public class VersionMappingDoc implements Serializable {
  @Id private String id;
  private String groupId;
  private String artifactId;
  private List<VersionPatternDoc> patterns;

  public VersionMappingDoc(VersionMapping versionMapping) {
    this.id = versionMapping.getId();
    this.groupId = versionMapping.getGroupId();
    this.artifactId = versionMapping.getArtifactId();
    this.patterns =
        versionMapping
            .getPatterns()
            .stream()
            .map(VersionPatternDoc::new)
            .collect(Collectors.toList());
  }

  public VersionMapping toDomain() {
    final VersionMapping domain = new VersionMapping(groupId, artifactId);
    if (patterns != null) {
      patterns.forEach(pattern -> domain.addPattern(pattern.toDomain()));
    }
    return domain;
  }
}
