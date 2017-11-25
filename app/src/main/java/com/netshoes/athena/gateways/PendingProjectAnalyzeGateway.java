package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.PendingProjectAnalyze;

public interface PendingProjectAnalyzeGateway {

  PendingProjectAnalyze findById(String id);

  void save(PendingProjectAnalyze pendingProjectAnalyze);
}
