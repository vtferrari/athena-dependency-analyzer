package com.netshoes.athena.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.github")
@Getter
@Setter
public class GitHubClientProperties {

  /** Host for GitHub API */
  private String host = "api.github.com";

  /** Username of GitHub, only required if token is not informed */
  private String username;

  /** Password of GitHub, only required if token is not informed */
  private String password;

  /** Token for access API */
  private String token;

  /** Organization in GitHub */
  private String organization;
}
