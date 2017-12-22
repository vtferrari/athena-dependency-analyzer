package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.gateways.http.jsons.ProjectJson;
import com.netshoes.athena.gateways.http.jsons.RequestProjectScanJson;
import com.netshoes.athena.usecases.ProjectScan;
import com.netshoes.athena.usecases.RequestProjectScan;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import com.netshoes.athena.usecases.exceptions.ProjectScanException;
import com.netshoes.athena.usecases.exceptions.RequestScanException;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
@Api(
  value = "/api/v1/projects",
  description = "Operations for scan projects",
  tags = "projects scan"
)
public class ProjectScanController {
  private final RequestProjectScan requestProjectScan;
  private final ProjectScan projectScan;

  @RequestMapping(path = "scan", produces = "application/json", method = RequestMethod.POST)
  @ApiOperation(
    value = "Request scan of dependencies for all projects for configured Source Control Manager",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ApiResponses(
    value = {
      @ApiResponse(
        code = 202,
        message = "Process started",
        response = RequestProjectScanJson.class,
        responseContainer = "List"
      ),
      @ApiResponse(code = 422, message = "Fail on start process", response = ErrorJson.class)
    }
  )
  @SuppressWarnings("unused")
  public List<RequestProjectScanJson> scan(@RequestHeader String authorization)
      throws RequestScanException, ScmApiRateLimitExceededException {
    final List<Project> projects =
        requestProjectScan.forMasterBranchToAllProjectsFromConfiguredOrganization();

    return projects.stream().map(RequestProjectScanJson::new).collect(Collectors.toList());
  }

  @RequestMapping(
    path = "/{projectId}/refresh",
    produces = "application/json",
    method = RequestMethod.POST
  )
  @ApiOperation(
    value = "Request a new scan for dependencies of project",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ApiResponses(value = {@ApiResponse(code = 202, message = "Process started")})
  @SuppressWarnings("unused")
  public RequestProjectScanJson refresh(
      @ApiParam(value = "Id of Project") @PathVariable("projectId") String projectId,
      @RequestHeader String authorization)
      throws ProjectNotFoundException {
    final Project project = requestProjectScan.refresh(projectId);

    return new RequestProjectScanJson(project);
  }

  @RequestMapping(
    path = "/{projectId}/refreshNow",
    produces = "application/json",
    method = RequestMethod.POST
  )
  @ApiOperation(
    value = "Do a synchronous new scan for dependencies of project",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Scan finished"),
      @ApiResponse(code = 422, message = "Scan failed", response = ErrorJson.class)
    }
  )
  @SuppressWarnings("unused")
  public ProjectJson refreshNow(
      @ApiParam(value = "Id of Project") @PathVariable("projectId") String projectId,
      @RequestHeader String authorization)
      throws ProjectNotFoundException, ProjectScanException, ScmApiRateLimitExceededException {

    final Project project = projectScan.execute(projectId);
    return new ProjectJson(project);
  }
}
