package com.netshoes.athena.gateways.job;

import com.netshoes.athena.usecases.RequestScanForPendingProjects;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PendingProjectAnalyzeJob {
  private RequestScanForPendingProjects requestScanForPendingProjects;

  @Scheduled(fixedDelay = 1000 * 60 * 5)
  public void run() {
    requestScanForPendingProjects.execute();
  }
}
