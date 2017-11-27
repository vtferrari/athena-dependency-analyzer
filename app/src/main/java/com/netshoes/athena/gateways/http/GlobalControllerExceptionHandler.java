package com.netshoes.athena.gateways.http;

import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.usecases.exceptions.RequestScanException;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
  private static final String REQUEST_SCAN_EXCEPTION_KEY = "requestScan.error";
  private static final String SCM_API_RATE_LIMIT_EXCEEDED_EXCEPTION_KEY =
      "scmApi.rateLimit.exceeded";
  private static final String EXCEPTION_KEY = "internal.error";

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(RequestScanException.class)
  public ErrorJson handleRequestScanException(RequestScanException e) {
    log.error(e.getMessage(), e);
    return new ErrorJson(REQUEST_SCAN_EXCEPTION_KEY, e.getMessage());
  }

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(ScmApiRateLimitExceededException.class)
  public ErrorJson handleRequestScanException(ScmApiRateLimitExceededException e) {
    log.error(e.getMessage(), e);
    final ErrorJson errorJson =
        new ErrorJson(SCM_API_RATE_LIMIT_EXCEEDED_EXCEPTION_KEY, e.getMessage());
    errorJson.putDetail("minutesToReset", e.getMinutesToReset().toString());

    return errorJson;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorJson handleException(Exception e) {
    log.error(e.getMessage(), e);
    return new ErrorJson(EXCEPTION_KEY, "Internal server error");
  }
}
