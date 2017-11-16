package com.netshoes.athena.gateways.http.jsons;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.http.DescriptorsController;
import com.netshoes.athena.gateways.http.ProjectsController;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Project")
@Slf4j
public class ProjectJson extends ResourceSupport {
  public static final String EXPAND_DESCRIPTORS = "descriptors";

  @ApiModelProperty(value = "Id of this project", required = true)
  private final String projectId;

  @ApiModelProperty(value = "Name of this project", required = true)
  private final String name;

  @ApiModelProperty(value = "Branch where scan was done", required = true)
  private final String branch;

  @ApiModelProperty(value = "Info about Source Control Management repository", required = true)
  private final ScmRepositoryJson scmRepository;

  @ApiModelProperty(value = "Date of last scan", required = true)
  private final OffsetDateTime lastCollectDate;

  public ProjectJson(Project domain) {
    final ScmRepository domainScmRepository = domain.getScmRepository();
    this.name = domain.getName();
    this.branch = domain.getBranch();
    this.scmRepository = new ScmRepositoryJson(domainScmRepository);
    this.projectId = domain.getId();
    this.lastCollectDate =
        OffsetDateTime.of(
            domain.getLastCollectDate(),
            ZoneOffset.systemDefault().getRules().getOffset(Instant.now()));

    buildLinks();
  }

  private void buildLinks() {
    try {
      final Link link = linkTo(methodOn(ProjectsController.class).get(projectId)).withSelfRel();
      add(link);

      final Link descriptorsLink =
          linkTo(methodOn(DescriptorsController.class).getDescriptors(projectId))
              .withRel("descriptors");
      add(descriptorsLink);
    } catch (Exception e) {
      log.error("Unable toPageable toPageable links", e);
    }
  }
}
