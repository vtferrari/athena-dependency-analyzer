package com.netshoes.athena.gateways.http.jsons;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.http.VersionMappingsController;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;

@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "VersionMapping")
@Slf4j
public class VersionMappingJson extends VersionMappingNotPersistedJson {

  @ApiModelProperty(value = "Generated with rule: groupId:artifactId")
  private String versionMappingId;

  public VersionMappingJson(VersionMapping versionMapping) {
    super(versionMapping);
    this.versionMappingId = versionMapping.getId();
    buildLinks();
  }

  private void buildLinks() {
    try {
      final Link link =
          linkTo(methodOn(VersionMappingsController.class).get(versionMappingId)).withSelfRel();
      add(link);
    } catch (Exception e) {
      log.error("Unable to create links", e);
    }
  }
}
