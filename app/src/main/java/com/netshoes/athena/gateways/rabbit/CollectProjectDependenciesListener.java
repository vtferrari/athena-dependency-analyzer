package com.netshoes.athena.gateways.rabbit;

import com.netshoes.athena.conf.RabbitMQConfiguration;
import com.netshoes.athena.gateways.rabbit.jsons.ProjectCollectRequest;
import com.netshoes.athena.usecases.CollectProjectDependencies;
import com.netshoes.athena.usecases.exceptions.DependencyCollectException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CollectProjectDependenciesListener {

  private final CollectProjectDependencies collectProjectDependencies;

  @RabbitListener(
    containerFactory = "applicationContainerFactory",
    queues = RabbitMQConfiguration.COLLECT_REPOSITORY_QUEUE
  )
  @Retryable
  public void process(ProjectCollectRequest projectCollectRequest)
      throws DependencyCollectException {

    final String repositoryId = projectCollectRequest.getRepositoryId();
    final String branch = projectCollectRequest.getBranch();

    collectProjectDependencies.execute(repositoryId, branch);
  }
}
