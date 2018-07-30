package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.ScmRepositoryContentData;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DependencyManagerGateway {

  Mono<DependencyManagementDescriptor> analyze(ScmRepositoryContentData content);

  Flux<DependencyManagementDescriptor> analyze(List<ScmRepositoryContentData> contents);
}
