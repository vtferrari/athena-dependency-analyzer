package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ScmApiRateLimit;
import com.netshoes.athena.gateways.ScmApiGetRateLimitException;
import com.netshoes.athena.gateways.github.GitHubScmGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetScmApiRateLimit {
  private final GitHubScmGateway gitHubScmGateway;

  public Mono<ScmApiRateLimit> execute() throws ScmApiGetRateLimitException {
    return gitHubScmGateway.getRateLimit();
  }
}
