package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.VersionPattern;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "VersionPattern")
@NoArgsConstructor
public class VersionPatternJson implements Serializable {

  @ApiModelProperty(
    value = "Pattern of version in regex",
    required = true,
    example = "1.(\\d+).(\\d+).GA"
  )
  private String pattern;

  @ApiModelProperty(value = "Version stable", required = true, example = "1.0.2.GA", position = 1)
  private String stable;

  @ApiModelProperty(value = "List of sub patterns", position = 2)
  private List<VersionPatternJson> patterns;

  public VersionPatternJson(VersionPattern versionPattern) {
    this.pattern = versionPattern.getPattern();
    this.stable = versionPattern.getStable();
    this.patterns =
        versionPattern
            .getPatterns()
            .stream()
            .map(VersionPatternJson::new)
            .collect(Collectors.toList());
  }

  public VersionPattern toDomain() {
    final VersionPattern domain = new VersionPattern(pattern, stable);
    if (patterns != null) {
      patterns.stream().map(VersionPatternJson::toDomain).forEach(domain::addVersionPattern);
    }
    return domain;
  }
}
