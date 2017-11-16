package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.gateways.http.jsons.RequestScanJson;
import com.netshoes.athena.usecases.RequestScan;
import com.netshoes.athena.usecases.exceptions.RequestScanException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
@Api(value = "/api/v1/projects", description = "Operations for scan projects", tags = "projects scan")
public class ScanController {
  private final RequestScan requestScan;

  @RequestMapping(path = "scan", produces = "application/json", method = RequestMethod.POST)
  @ApiOperation(
    value = "Scan projects dependencies for configured Source Control Manager",
    produces = "application/json"
  )
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ApiResponses(
    value = {
      @ApiResponse(
        code = 202,
        message = "Process started",
        response = RequestScanJson.class,
        responseContainer = "List"
      )
    }
  )
  public List<RequestScanJson> scan() throws RequestScanException {
    final List<Project> projects =
        requestScan.forMasterBranchToAllProjectsFromConfiguredOrganization();

    return projects.stream().map(RequestScanJson::new).collect(Collectors.toList());
  }
}
