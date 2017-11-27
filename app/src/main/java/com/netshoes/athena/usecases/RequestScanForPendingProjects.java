package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmApiRateLimit;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.PendingProjectAnalyzeGateway;
import com.netshoes.athena.gateways.ScmApiGetRateLimitException;
import com.netshoes.athena.gateways.ScmGateway;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RequestScanForPendingProjects {

  public static final int MINIMUM_REQUESTS_REQUIRED = 100;
  private final PendingProjectAnalyzeGateway pendingProjectAnalyzeGateway;
  private final ScmGateway scmGateway;
  private final RequestProjectScan requestProjectScan;

  public void execute() {
    try {
      final ScmApiRateLimit rateLimit = scmGateway.getRateLimit();
      final int remainingRequests = rateLimit.getRate().getRemaining();
      if (remainingRequests >= MINIMUM_REQUESTS_REQUIRED) {
        try (Stream<PendingProjectAnalyze> stream = pendingProjectAnalyzeGateway.readAll()) {
          stream.forEach(this::execute);
        }
      } else {
        log.info(
            "There are {} requests remaining, ignoring this scan for pending projects",
            remainingRequests);
      }
    } catch (ScmApiGetRateLimitException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void execute(PendingProjectAnalyze pendingProjectAnalyze) {
    final Project project = pendingProjectAnalyze.getProject();
    final ScmRepository repository = project.getScmRepository();
    requestProjectScan.forBranchOfRepository(project.getBranch(), repository);
  }
}
