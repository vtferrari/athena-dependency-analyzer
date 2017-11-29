package com.netshoes.athena.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.rabbitmq.consumers")
@Getter
@Setter
public class RabbitMQConsumerProperties {

  private int projectScanQueueConcurrentConsumers;
  private int projectScanQueueMaxConcurrentConsumers;
  private int projectDependenciesAnalyzeQueueConcurrentConsumers;
  private int projectDependenciesAnalyzeQueueMaxConcurrentConsumers;
}
