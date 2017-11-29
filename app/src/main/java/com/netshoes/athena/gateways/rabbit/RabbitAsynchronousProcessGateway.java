package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectDependenciesAnalyzeRequestJson;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectScanRequestJson;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitAsynchronousProcessGateway implements AsynchronousProcessGateway {

  private final RabbitTemplate rabbitTemplate;
  private final Binding projectScanBinding;
  private final Binding projectDependenciesAnalyzeBinding;

  @Override
  public void requestProjectScan(Project project) {
    final ScmRepository repository = project.getScmRepository();
    final String repositoryId = repository.getId();
    final ProjectScanRequestJson request =
        new ProjectScanRequestJson(project.getId(), repositoryId, project.getBranch());

    rabbitTemplate.convertAndSend(
        projectScanBinding.getExchange(), projectScanBinding.getRoutingKey(), request);
  }

  @Override
  public void requestProjectDependencyAnalyze(Project project) {
    final ProjectDependenciesAnalyzeRequestJson request =
        new ProjectDependenciesAnalyzeRequestJson(project.getId());

    rabbitTemplate.convertAndSend(
        projectDependenciesAnalyzeBinding.getExchange(),
        projectDependenciesAnalyzeBinding.getRoutingKey(),
        request);
  }
}
