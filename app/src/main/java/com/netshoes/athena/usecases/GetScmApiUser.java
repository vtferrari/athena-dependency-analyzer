package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.gateways.ScmApiGatewayRateLimitExceededException;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetScmApiUser {
  private final ScmGateway scmGateway;

  public ScmApiUser execute() throws ScmApiRateLimitExceededException {
    try {
      return scmGateway.getApiUser();
    } catch (ScmApiGatewayRateLimitExceededException e) {
      throw new ScmApiRateLimitExceededException(e, e.getMinutesToReset());
    }
  }
}
