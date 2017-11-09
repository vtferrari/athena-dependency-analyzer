package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.gateways.ScmGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GetScmApiUser {
  private final ScmGateway scmGateway;

  public ScmApiUser execute() {
    return scmGateway.getApiUser();
  }
}
