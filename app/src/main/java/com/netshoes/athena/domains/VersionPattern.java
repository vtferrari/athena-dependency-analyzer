package com.netshoes.athena.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VersionPattern implements Serializable {
  private final String pattern;
  private final String stable;
  private final List<VersionPattern> patterns;
  private Pattern compiledPattern;

  public VersionPattern(String pattern, String stable) {
    this.pattern = pattern;
    this.stable = stable;
    this.patterns = new ArrayList<>();
  }

  protected VersionPattern(String pattern, String stable, List<VersionPattern> patterns) {
    this.pattern = pattern;
    this.stable = stable;
    this.patterns = patterns;
  }

  public VersionPattern addVersionPattern(VersionPattern versionPattern) {
    patterns.add(versionPattern);
    return this;
  }

  public ArtifactVersionReport check(final Artifact artifact) {
    final Pattern compiledPattern = getCompiledPattern();

    final Matcher m = compiledPattern.matcher(artifact.getVersion());
    boolean isStable = false;
    boolean matched = m.find();
    final List<ArtifactVersionReport> subReports = new ArrayList<>();
    if (matched) {
      patterns
          .stream()
          .map(versionPattern -> versionPattern.check(artifact))
          .filter(this::isMatchedAndNotEqualsToThisStableVersion)
          .findFirst()
          .ifPresent(subReports::add);

      isStable = stable.equals(artifact.getVersion());
    }

    final ArtifactVersionReport report = new ArtifactVersionReport(matched, isStable, stable);
    subReports.stream().forEach(report::addAlternative);
    return report;
  }

  private Pattern getCompiledPattern() {
    if (compiledPattern == null) {
      compiledPattern = Pattern.compile(this.pattern);
    }
    return compiledPattern;
  }

  private boolean isMatchedAndNotEqualsToThisStableVersion(ArtifactVersionReport report) {
    return report.isMatched() && !report.getStableVersion().equals(this.stable);
  }
}
