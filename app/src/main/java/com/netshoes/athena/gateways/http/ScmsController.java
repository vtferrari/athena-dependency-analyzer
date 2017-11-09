package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.gateways.http.jsons.ScmApiUserJson;
import com.netshoes.athena.usecases.GetScmApiUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scms/user")
@AllArgsConstructor
@Api(value = "/api/v1/scms/user", description = "Operations in SCMs", tags = "scms")
public class ScmsController {
  private final GetScmApiUser getScmApiUser;

  @RequestMapping(produces = "application/json", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get API user data", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success", response = ScmApiUserJson.class),
      @ApiResponse(code = 404, message = "Not found")
    }
  )
  public ScmApiUserJson getUser() {
    final ScmApiUser scmApiUser = getScmApiUser.execute();
    return new ScmApiUserJson(scmApiUser);
  }
}
