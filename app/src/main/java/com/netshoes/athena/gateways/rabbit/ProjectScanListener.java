package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.conf.RabbitMQConfiguration;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectScanRequestJson;
import com.netshoes.athena.usecases.ProjectScan;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectScanListener {

  private final ProjectScan projectScan;

  @RabbitListener(
      containerFactory = "projectScanContainerFactory",
      queues = RabbitMQConfiguration.PROJECT_SCAN_QUEUE)
  public void process(ProjectScanRequestJson projectScanRequest) {
    final String projectId = projectScanRequest.getProjectId();
    final String repositoryId = projectScanRequest.getRepositoryId();
    final String branch = projectScanRequest.getBranch();

    projectScan.execute(projectId, repositoryId, branch).block();
  }
}
