package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.PaginatedResponse;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.RequestOfPage;
import com.netshoes.athena.gateways.http.jsons.DependencyManagementDescriptorJson;
import com.netshoes.athena.gateways.http.jsons.PageableResourceSupport;
import com.netshoes.athena.gateways.http.jsons.ProjectJson;
import com.netshoes.athena.gateways.http.jsons.RequestProjectCollectJson;
import com.netshoes.athena.usecases.GetProjects;
import com.netshoes.athena.usecases.RequestCollectProjects;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import com.netshoes.athena.usecases.exceptions.RequestCollectProjectException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
@Api(value = "/api/v1/projects", description = "Operations in projects", tags = "projects")
public class ProjectsController {

  private final GetProjects getProjects;
  private final RequestCollectProjects requestCollectProjects;

  @RequestMapping(produces = "application/json", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "List projects", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success", response = ProjectsPageableJson.class),
    }
  )
  public ProjectsPageableJson list(
      @ApiParam(value = "Number of page", required = true) @RequestParam Integer pageNumber,
      @ApiParam(value = "Size of page", defaultValue = "20")
          @RequestParam(required = false, defaultValue = "20")
          Integer pageSize) {
    final PaginatedResponse<Project> page =
        getProjects.all(new RequestOfPage(pageNumber, pageSize));

    final ProjectsPageableJson pageJson = new ProjectsPageableJson();
    pageJson.initialize(page, ProjectJson::new, pageNumber, pageSize);
    return pageJson;
  }

  @RequestMapping(path = "/{id}", produces = "application/json", method = RequestMethod.GET)
  @ApiOperation(value = "Get project by id", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success", response = ProjectJson.class),
      @ApiResponse(code = 404, message = "Project not found")
    }
  )
  public ProjectJson get(@ApiParam(value = "Id of Project") @PathVariable("id") String id)
      throws ProjectNotFoundException {
    final Project project = getProjects.byId(id);
    return new ProjectJson(project);
  }

  @RequestMapping(
    path = "/{id}/descriptors",
    produces = "application/json",
    method = RequestMethod.GET
  )
  @ApiOperation(value = "Get descriptors of project", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(
        code = 200,
        message = "Success",
        response = DependencyManagementDescriptorJson.class,
        responseContainer = "List"
      ),
      @ApiResponse(code = 404, message = "Project not found")
    }
  )
  public List<DependencyManagementDescriptorJson> getDescriptors(
      @ApiParam(value = "Id of Project") @PathVariable("id") String id)
      throws ProjectNotFoundException {

    final Project project = getProjects.byId(id);

    return project
        .getDescriptors()
        .stream()
        .map(DependencyManagementDescriptorJson::new)
        .collect(Collectors.toList());
  }

  @RequestMapping(path = "collect", produces = "application/json", method = RequestMethod.POST)
  @ApiOperation(
    value = "Collect projects dependencies for configured Source Control Manager",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ApiResponses(
    value = {
      @ApiResponse(
        code = 202,
        message = "Process started",
        response = RequestProjectCollectJson.class,
        responseContainer = "List"
      )
    }
  )
  public List<RequestProjectCollectJson> collect() throws RequestCollectProjectException {
    final List<Project> projects =
        requestCollectProjects.forMasterBranchToAllProjectsFromConfiguredOrganization();

    return projects.stream().map(RequestProjectCollectJson::new).collect(Collectors.toList());
  }

  private class ProjectsPageableJson extends PageableResourceSupport<Project, ProjectJson> {}
}
