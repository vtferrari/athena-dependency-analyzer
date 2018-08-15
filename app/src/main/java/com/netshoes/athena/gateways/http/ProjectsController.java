package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.RequestOfPage;
import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.gateways.http.jsons.ProjectJson;
import com.netshoes.athena.usecases.GetProjects;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
@Api(value = "/api/v1/projects", description = "Operations in projects", tags = "projects")
public class ProjectsController {
  private final GetProjects getProjects;

  @RequestMapping(produces = "application/json", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "List projects", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = ProjectJson.class),
      })
  public Flux<ProjectJson> list(
      @ApiParam(value = "Number of page", required = true) @RequestParam final Integer pageNumber,
      @ApiParam(value = "Size of page", defaultValue = "20")
          @RequestParam(required = false, defaultValue = "20")
          final Integer pageSize,
      @ApiParam(value = "Partial or complete name of project") @RequestParam(required = false)
          String name) {

    Flux<Project> projects;
    if (name != null) {
      projects = getProjects.search(new RequestOfPage(pageNumber, pageSize), name);
    } else {
      projects = getProjects.all(new RequestOfPage(pageNumber, pageSize));
    }
    return projects.map(ProjectJson::new);
  }

  @RequestMapping(value = "/count", produces = "application/json", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Count projects", produces = "application/json")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
  public Mono<Long> count(@RequestParam(required = false) String name) {
    Mono<Long> count;
    if (name != null) {
      count = getProjects.countSearch(name);
    } else {
      count = getProjects.countSearch();
    }
    return count;
  }

  @RequestMapping(path = "/{id}", produces = "application/json", method = RequestMethod.GET)
  @ApiOperation(value = "Get project by id", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = ProjectJson.class),
        @ApiResponse(code = 404, message = "Project not found", response = ErrorJson.class)
      })
  public Mono<ProjectJson> get(@ApiParam(value = "Id of Project") @PathVariable("id") String id) {
    return getProjects.byId(id).map(ProjectJson::new);
  }
}
