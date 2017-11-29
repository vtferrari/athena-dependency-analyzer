package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.Project;

public interface AsynchronousProcessGateway {

  void requestProjectScan(Project project);

  void requestProjectDependencyAnalyze(Project project);
}
