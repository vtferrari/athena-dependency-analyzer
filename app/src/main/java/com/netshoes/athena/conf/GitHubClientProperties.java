package com.netshoes.athena.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.github")
@Getter
@Setter
public class GitHubClientProperties {

  private String host;
  private String username;
  private String password;
}
