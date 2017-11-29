package com.netshoes.athena.conf;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RabbitMQConsumerProperties.class})
@AllArgsConstructor
public class RabbitMQConfiguration {

  public static final String PROJECT_SCAN_QUEUE = "athena.project.scan";
  public static final String PROJECT_ANALYZE_DEPENDENCIES_QUEUE =
      "athena.project.dependencies.analyze";
  private final RabbitMQConsumerProperties rabbitMQConsumerProperties;

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue projectScanQueue() {
    return new Queue(PROJECT_SCAN_QUEUE);
  }

  @Bean
  public Queue projectDependenciesAnalyzeQueue() {
    return new Queue(PROJECT_ANALYZE_DEPENDENCIES_QUEUE);
  }

  @Bean
  public TopicExchange projectScanExchange() {
    return new TopicExchange("athena.project.scan");
  }

  @Bean
  public TopicExchange projectDependenciesAnalyzeExchange() {
    return new TopicExchange("athena.project.dependencies.analyze");
  }

  @Bean
  public Binding projectScanBinding(TopicExchange projectScanExchange, Queue projectScanQueue) {
    return BindingBuilder.bind(projectScanQueue)
        .to(projectScanExchange)
        .with("athena.project.scan");
  }

  @Bean
  public Binding projectDependenciesAnalyzeBinding(
      TopicExchange projectDependenciesAnalyzeExchange, Queue projectDependenciesAnalyzeQueue) {
    return BindingBuilder.bind(projectDependenciesAnalyzeQueue)
        .to(projectDependenciesAnalyzeExchange)
        .with("athena.project.dependencies.analyze");
  }

  @Bean
  public SimpleRabbitListenerContainerFactory projectScanContainerFactory(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrentConsumers(
        rabbitMQConsumerProperties.getProjectScanQueueConcurrentConsumers());
    factory.setMaxConcurrentConsumers(
        rabbitMQConsumerProperties.getProjectScanQueueMaxConcurrentConsumers());
    factory.setMessageConverter(messageConverter);
    return factory;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory projectDependenciesAnalyzerContainerFactory(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrentConsumers(
        rabbitMQConsumerProperties.getProjectDependenciesAnalyzeQueueConcurrentConsumers());
    factory.setMaxConcurrentConsumers(
        rabbitMQConsumerProperties.getProjectDependenciesAnalyzeQueueMaxConcurrentConsumers());
    factory.setMessageConverter(messageConverter);
    return factory;
  }
}
