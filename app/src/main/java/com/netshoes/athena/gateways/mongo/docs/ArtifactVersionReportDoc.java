package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.ArtifactVersionReport;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtifactVersionReportDoc {
  private boolean matched;
  private boolean stable;
  private String stableVersion;
  private String summary;
  private List<ArtifactVersionReportDoc> alternatives;

  public ArtifactVersionReportDoc(ArtifactVersionReport domain) {
    this.matched = domain.isMatched();
    this.stable = domain.isStable();
    this.stableVersion = domain.getStableVersion();
    this.summary = domain.getSummary();
    this.alternatives =
        domain
            .getAlternatives()
            .stream()
            .map(ArtifactVersionReportDoc::new)
            .collect(Collectors.toList());
  }

  public ArtifactVersionReport toDomain() {
    final ArtifactVersionReport domain =
        new ArtifactVersionReport(this.matched, this.stable, this.stableVersion);

    if (alternatives != null) {
      alternatives.forEach(a -> domain.addAlternative(a.toDomain()));
    }
    return domain;
  }
}
