package com.netshoes.athena.gateways.http;

import com.netshoes.athena.gateways.http.jsons.DependencyManagementDescriptorJson;
import com.netshoes.athena.usecases.GetDescriptors;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/descriptors")
@AllArgsConstructor
@Api(
    value = "/api/v1/projects/{projectId}/descriptors",
    description = "Operations in descriptors of projects",
    tags = "projects descriptors")
public class DescriptorsController {
  private final GetDescriptors getDescriptors;

  @RequestMapping(produces = "application/json", method = RequestMethod.GET)
  @ApiOperation(value = "Get descriptors of project", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Success",
            response = DependencyManagementDescriptorJson.class,
            responseContainer = "List"),
        @ApiResponse(code = 404, message = "Project not found")
      })
  public Flux<DependencyManagementDescriptorJson> list(
      @ApiParam(value = "Id of Project", required = true) @PathVariable("projectId")
          String projectId) {

    return getDescriptors.byProject(projectId).map(DependencyManagementDescriptorJson::new);
  }

  @RequestMapping(path = "/{id}", produces = "application/json", method = RequestMethod.GET)
  @ApiOperation(value = "Get descriptor of project by id", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Success",
            response = DependencyManagementDescriptorJson.class),
        @ApiResponse(code = 404, message = "Descriptor not found")
      })
  public Mono<DependencyManagementDescriptorJson> getDescriptorById(
      @ApiParam(value = "Id of Project", required = true) @PathVariable("projectId")
          String projectId,
      @ApiParam(value = "Id of Descriptor", required = true) @PathVariable("id") String id) {

    return getDescriptors.byId(projectId, id).map(DependencyManagementDescriptorJson::new);
  }
}
