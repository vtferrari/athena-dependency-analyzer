package com.netshoes.athena.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.rabbitmq.consumers")
@Getter
public class RabbitMQConsumerProperties {
  private final Consumer projectScan = new Consumer();
  private final Consumer projectDependenciesAnalyze = new Consumer();

  @Getter
  @Setter
  public static class Consumer {
    private int concurrency;
    private int maxConcurrency;
  }
}
