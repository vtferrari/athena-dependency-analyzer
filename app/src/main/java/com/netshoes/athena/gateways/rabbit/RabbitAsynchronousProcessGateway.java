package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.gateways.AsynchronousProcessGateway;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectCollectRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitAsynchronousProcessGateway implements AsynchronousProcessGateway {

  private final RabbitTemplate rabbitTemplate;
  private final Binding analyzeRepositoryBinding;

  @Override
  public void requestDependencyAnalyze(Project project) {
    final ScmRepository repository = project.getScmRepository();
    final String repositoryId = repository.getId();
    final ProjectCollectRequest request =
        new ProjectCollectRequest(repositoryId, project.getBranch());

    rabbitTemplate.convertAndSend(
        analyzeRepositoryBinding.getExchange(), analyzeRepositoryBinding.getRoutingKey(), request);
  }
}
