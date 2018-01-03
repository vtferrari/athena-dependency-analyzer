package com.netshoes.athena.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.rabbitmq.consumers")
@Getter
@Setter
public class RabbitMQConsumerProperties {

  /** Configuration for consumers for project scan */
  private final Consumer projectScan = new Consumer();

  /** Configuration for consumers for project dependencies analyzes */
  private final Consumer projectDependenciesAnalyze = new Consumer();

  @Getter
  @Setter
  public static class Consumer {

    /** Default quantity of concurrent consumers */
    private int concurrency;

    /** Max quantity of concurrent consumers */
    private int maxConcurrency;
  }
}
