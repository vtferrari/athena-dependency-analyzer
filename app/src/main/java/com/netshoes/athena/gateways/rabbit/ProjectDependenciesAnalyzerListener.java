package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.conf.RabbitMQConfiguration;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectDependenciesAnalyzeRequestJson;
import com.netshoes.athena.usecases.AnalyzeProjectDependencies;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectDependenciesAnalyzerListener {

  private final AnalyzeProjectDependencies analyzeProjectDependencies;

  @RabbitListener(
    containerFactory = "projectDependenciesAnalyzerContainerFactory",
    queues = RabbitMQConfiguration.PROJECT_ANALYZE_DEPENDENCIES_QUEUE
  )
  public void process(ProjectDependenciesAnalyzeRequestJson json) throws ProjectNotFoundException {
    final String projectId = json.getProjectId();
    analyzeProjectDependencies.execute(projectId);
  }
}
