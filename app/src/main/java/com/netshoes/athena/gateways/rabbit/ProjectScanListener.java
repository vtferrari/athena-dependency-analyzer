package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.conf.RabbitMQConfiguration;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectScanRequestJson;
import com.netshoes.athena.usecases.ProjectScan;
import com.netshoes.athena.usecases.exceptions.ProjectScanException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectScanListener {

  private final ProjectScan projectScan;

  @RabbitListener(
    containerFactory = "applicationContainerFactory",
    queues = RabbitMQConfiguration.PROJECT_SCAN_QUEUE
  )
  @Retryable
  public void process(ProjectScanRequestJson projectScanRequest) throws ProjectScanException {

    final String projectId = projectScanRequest.getProjectId();
    final String repositoryId = projectScanRequest.getRepositoryId();
    final String branch = projectScanRequest.getBranch();

    projectScan.execute(projectId, repositoryId, branch);
  }
}
