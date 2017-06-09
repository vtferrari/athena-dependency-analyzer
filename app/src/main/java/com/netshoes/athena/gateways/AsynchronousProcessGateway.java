package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.Project;

public interface AsynchronousProcessGateway {

  void requestDependencyAnalyze(Project project);
}
