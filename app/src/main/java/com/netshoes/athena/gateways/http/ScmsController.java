package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.ScmApiRateLimit;
import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.gateways.ScmApiGetRateLimitException;
import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.gateways.http.jsons.ScmApiRateLimitJson;
import com.netshoes.athena.gateways.http.jsons.ScmApiUserJson;
import com.netshoes.athena.usecases.GetScmApiRateLimit;
import com.netshoes.athena.usecases.GetScmApiUser;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/scms")
@AllArgsConstructor
@Api(value = "/api/v1/scms", description = "Operations in SCMs", tags = "scms")
public class ScmsController {
  private final GetScmApiUser getScmApiUser;
  private final GetScmApiRateLimit getScmApiRateLimit;

  @RequestMapping(path = "/user", produces = "application/json", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get API user data", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success", response = ScmApiUserJson.class),
      @ApiResponse(code = 422, message = "Error", response = ErrorJson.class),
      @ApiResponse(code = 404, message = "Not found")
    }
  )
  @SuppressWarnings("unused")
  public Mono<ScmApiUserJson> getUser(@RequestHeader String authorization)
      throws ScmApiRateLimitExceededException {
    final Mono<ScmApiUser> scmApiUser = getScmApiUser.execute();
    return scmApiUser.map(ScmApiUserJson::new);
  }

  @RequestMapping(path = "/rateLimit", produces = "application/json", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get rate limit", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success", response = ScmApiRateLimitJson.class),
      @ApiResponse(code = 422, message = "Error", response = ErrorJson.class),
      @ApiResponse(code = 404, message = "Not found")
    }
  )
  @SuppressWarnings("unused")
  public Mono<ScmApiRateLimitJson> rateLimit(@RequestHeader String authorization)
      throws ScmApiGetRateLimitException {
    final Mono<ScmApiRateLimit> scmApiRateLimit = getScmApiRateLimit.execute();
    return scmApiRateLimit.map(ScmApiRateLimitJson::new);
  }
}
