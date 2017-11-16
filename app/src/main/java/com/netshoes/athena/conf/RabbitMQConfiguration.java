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
  private final RabbitMQConsumerProperties rabbitMQConsumerProperties;

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue analyzeRepositoryQueue() {
    return new Queue(PROJECT_SCAN_QUEUE);
  }

  @Bean
  public TopicExchange analyzeRepositoryExchange() {
    return new TopicExchange("athena.project.scan");
  }

  @Bean
  public Binding analyzeRepositoryBinding(
      TopicExchange analyzeRepositoryExchange, Queue analyzeRepositoryQueue) {
    return BindingBuilder.bind(analyzeRepositoryQueue)
        .to(analyzeRepositoryExchange)
        .with("athena.project.scan");
  }

  @Bean
  public SimpleRabbitListenerContainerFactory applicationContainerFactory(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrentConsumers(rabbitMQConsumerProperties.getCollectQueueConcurrentConsumers());
    factory.setMaxConcurrentConsumers(
        rabbitMQConsumerProperties.getCollectQueueMaxConcurrentConsumers());
    factory.setMessageConverter(messageConverter);
    return factory;
  }
}
