package com.netshoes.athena.conf;

import lombok.AllArgsConstructor;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.DataService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({GitHubClientProperties.class})
@AllArgsConstructor
public class GitHubClientConfiguration {

  private final GitHubClientProperties properties;

  @Bean
  public GitHubClient gitHubClient() {
    final GitHubClient client = new GitHubClient(properties.getHost());
    client.setCredentials(properties.getUsername(), properties.getPassword());
    return client;
  }

  @Bean
  public RepositoryService repositoryService(GitHubClient gitHubClient) {
    return new RepositoryService(gitHubClient);
  }

  @Bean
  public ContentsService contentsService(GitHubClient gitHubClient) {
    return new ContentsService(gitHubClient);
  }

  @Bean
  public DataService dataService(GitHubClient gitHubClient) {
    return new DataService(gitHubClient);
  }

  @Bean
  public UserService userService(GitHubClient gitHubClient) {
    return new UserService(gitHubClient);
  }
}
