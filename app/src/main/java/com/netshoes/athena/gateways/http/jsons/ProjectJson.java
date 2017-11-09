package com.netshoes.athena.gateways.http.jsons;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.http.ProjectsController;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Project")
public class ProjectJson extends ResourceSupport {

  @ApiModelProperty(value = "Id of this project", required = true)
  private final String projectId;

  @ApiModelProperty(value = "Name of this project", required = true)
  private final String name;

  @ApiModelProperty(value = "Branch where collect was done", required = true)
  private final String branch;

  @ApiModelProperty("List of dependency management descriptors")
  private final List<DependencyManagementDescriptorJson> descriptors;

  @ApiModelProperty(value = "Info about Source Control Management repository", required = true)
  private final ScmRepositoryJson scmRepository;

  @ApiModelProperty(value = "Date of last collect", required = true)
  private final OffsetDateTime lastCollectDate;

  public ProjectJson(Project domain) {
    final ScmRepository domainScmRepository = domain.getScmRepository();
    final List<DependencyManagementDescriptor> domainDescriptors = domain.getDescriptors();

    if (domainDescriptors != null && !domainDescriptors.isEmpty()) {
      this.descriptors =
          domainDescriptors
              .stream()
              .map(DependencyManagementDescriptorJson::new)
              .collect(Collectors.toList());
    } else {
      this.descriptors = null;
    }
    this.name = domain.getName();
    this.branch = domain.getBranch();
    this.scmRepository = new ScmRepositoryJson(domainScmRepository);
    this.projectId = domain.getId();
    this.lastCollectDate =
        OffsetDateTime.of(
            domain.getLastCollectDate(),
            ZoneOffset.systemDefault().getRules().getOffset(Instant.now()));

    final Link link = linkTo(ProjectsController.class).slash(projectId).withSelfRel();
    add(link);
  }
}
