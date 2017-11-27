package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ScmApiRateLimit;
import com.netshoes.athena.gateways.ScmApiGetRateLimitException;
import com.netshoes.athena.gateways.github.GitHubScmGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetScmApiRateLimit {
  private final GitHubScmGateway gitHubScmGateway;

  public ScmApiRateLimit execute() throws ScmApiGetRateLimitException {
    return gitHubScmGateway.getRateLimit();
  }
}
