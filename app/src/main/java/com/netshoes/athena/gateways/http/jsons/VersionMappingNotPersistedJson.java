package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.VersionMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "VersionMappingNotPersisted", subTypes = VersionMappingJson.class)
@NoArgsConstructor
@Slf4j
public class VersionMappingNotPersistedJson extends ResourceSupport {

  @ApiModelProperty(value = "Group id", example = "com.netshoes", required = true, position = 1)
  @NotEmpty
  private String groupId;

  @ApiModelProperty(
      value = "Artifact id",
      example = "athena-dependency-analyzer",
      required = true,
      position = 2)
  @NotEmpty
  private String artifactId;

  @ApiModelProperty(value = "List of patterns for this artifact", position = 3)
  @NotEmpty
  private List<VersionPatternJson> patterns;

  public VersionMappingNotPersistedJson(VersionMapping versionMapping) {
    this.patterns =
        versionMapping
            .getPatterns()
            .stream()
            .map(VersionPatternJson::new)
            .collect(Collectors.toList());
  }

  public VersionMapping toDomain() {
    final VersionMapping versionMapping = new VersionMapping(groupId, artifactId);
    if (patterns != null) {
      patterns
          .stream()
          .map(VersionPatternJson::toDomain)
          .forEach(p -> versionMapping.addPattern(p));
    }
    return versionMapping;
  }
}
