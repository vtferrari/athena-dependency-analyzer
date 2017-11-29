package com.netshoes.athena.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ArtifactVersionReport implements Serializable {
  private final boolean matched;
  private final boolean stable;
  private final String stableVersion;
  private final List<ArtifactVersionReport> alternatives;

  public ArtifactVersionReport(boolean matched, boolean stable, String stableVersion) {
    this.matched = matched;
    this.stable = stable;
    this.stableVersion = stableVersion;
    this.alternatives = new ArrayList<>();
  }

  public ArtifactVersionReport addAlternative(ArtifactVersionReport report) {
    this.alternatives.add(report);
    return this;
  }

  private boolean isDifferentStableVersion(ArtifactVersionReport alternative) {
    return !alternative.getStableVersion().equals(stableVersion);
  }

  public String getSummary(boolean withAlternatives) {
    if (withAlternatives) {
      final StringBuilder sb = new StringBuilder(getSummaryWithoutAlternatives());

      alternatives
          .stream()
          .filter(this::isDifferentStableVersion)
          .filter(a -> !a.isStable())
          .forEach(alternative -> sb.append(" or ").append(alternative.getSummary()));

      return sb.toString();
    } else {
      return getSummaryWithoutAlternatives();
    }
  }

  private String getSummaryWithoutAlternatives() {
    String summary;
    if (matched) {
      final StringBuilder sb = new StringBuilder();
      if (stable) {
        sb.append("Stable version ");
        sb.append(stableVersion);
      } else {
        sb.append("Upgrade to version ");
        sb.append(stableVersion);
      }
      summary = sb.toString();
    } else {
      summary = "";
    }
    return summary;
  }

  public String getSummary() {
    return getSummary(true);
  }
}
