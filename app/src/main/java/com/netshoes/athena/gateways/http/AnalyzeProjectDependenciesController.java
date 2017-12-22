package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.gateways.http.jsons.ProjectJson;
import com.netshoes.athena.usecases.AnalyzeProjectDependencies;
import com.netshoes.athena.usecases.RequestProjectDependenciesAnalyze;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
@Api(
  value = "/api/v1/projects",
  description = "Operations for analyze dependencies of projects",
  tags = "projects analyze"
)
public class AnalyzeProjectDependenciesController {
  private final AnalyzeProjectDependencies analyzeProjectDependencies;
  private final RequestProjectDependenciesAnalyze requestProjectDependenciesAnalyze;

  @PostMapping(path = "/{projectId}/dependencies/analyzeNow", produces = "application/json")
  @ApiOperation(
    value = "Analyze dependencies and store the result for each artifact",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Analyze finished", response = ProjectJson.class),
      @ApiResponse(code = 404, message = "Project not found"),
      @ApiResponse(code = 422, message = "Analyze failed", response = ErrorJson.class)
    }
  )
  @SuppressWarnings("unused")
  public ProjectJson analyzeNow(
      @ApiParam(value = "Id of Project") @PathVariable("projectId") String projectId,
      @RequestHeader String authorization)
      throws ProjectNotFoundException {
    final Project project = analyzeProjectDependencies.execute(projectId);
    return new ProjectJson(project);
  }

  @PostMapping(path = "/dependencies/analyze", produces = "application/json")
  @ApiOperation(
    value = "Request dependencies analyze and store the result",
    produces = "application/json"
  )
  @SuppressWarnings("unused")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void analyze(@RequestHeader String authorization) {
    requestProjectDependenciesAnalyze.forAllProjects();
  }
}
