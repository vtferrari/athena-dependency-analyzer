package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.gateways.ScmApiGatewayRateLimitExceededException;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.usecases.exceptions.ScmApiRateLimitExceededException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetScmApiUser {
  private final ScmGateway scmGateway;

  public Mono<ScmApiUser> execute() throws ScmApiRateLimitExceededException {
    return scmGateway
        .getApiUser()
        .onErrorMap(
            ScmApiGatewayRateLimitExceededException.class, ScmApiRateLimitExceededException::new);
  }
}
