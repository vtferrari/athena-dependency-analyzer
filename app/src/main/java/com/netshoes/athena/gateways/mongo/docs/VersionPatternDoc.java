package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.VersionPattern;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "pattern")
@Data
public class VersionPatternDoc implements Serializable {
  private String pattern;
  private String stable;
  private List<VersionPatternDoc> patterns;

  public VersionPatternDoc(VersionPattern versionPattern) {
    this.pattern = versionPattern.getPattern();
    this.stable = versionPattern.getStable();
    this.patterns =
        versionPattern
            .getPatterns()
            .stream()
            .map(VersionPatternDoc::new)
            .collect(Collectors.toList());
  }

  public VersionPattern toDomain() {
    final VersionPattern domain = new VersionPattern(pattern, stable);
    if (patterns != null) {
      patterns
          .stream()
          .map(VersionPatternDoc::toDomain)
          .forEach(pattern -> pattern.addVersionPattern(pattern));
    }
    return domain;
  }
}
