package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.Project;
import reactor.core.publisher.Mono;

public interface AsynchronousProcessGateway {

  Mono<Void> requestProjectScan(Project project);

  Mono<Void> requestProjectDependencyAnalyze(Project project);
}
