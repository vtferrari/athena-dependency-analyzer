package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import java.util.stream.Stream;

public interface PendingProjectAnalyzeGateway {

  PendingProjectAnalyze findById(String id);

  Stream<PendingProjectAnalyze> readAll();

  void delete(String id);

  void save(PendingProjectAnalyze pendingProjectAnalyze);
}
