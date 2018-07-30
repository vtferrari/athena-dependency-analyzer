package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.conf.RabbitMQConfiguration;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectDependenciesAnalyzeRequestJson;
import com.netshoes.athena.usecases.AnalyzeProjectDependencies;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProjectDependenciesAnalyzerListener {

  private final AnalyzeProjectDependencies analyzeProjectDependencies;

  public ProjectDependenciesAnalyzerListener(
      AnalyzeProjectDependencies analyzeProjectDependencies) {
    this.analyzeProjectDependencies = analyzeProjectDependencies;
  }

  @RabbitListener(
      containerFactory = "projectDependenciesAnalyzerContainerFactory",
      queues = RabbitMQConfiguration.PROJECT_ANALYZE_DEPENDENCIES_QUEUE)
  public void process(ProjectDependenciesAnalyzeRequestJson json) {
    analyzeProjectDependencies.analyzeProject(json.getProjectId()).block();
  }
}
