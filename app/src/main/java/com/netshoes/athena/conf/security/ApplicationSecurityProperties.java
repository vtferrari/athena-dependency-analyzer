package com.netshoes.athena.conf.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("application.security")
public class ApplicationSecurityProperties {
  private String resourceId;
  private Token token = new Token();
  private User admin = new User();

  @Getter
  @Setter
  public static final class User {
    private String username = "admin";
    private String password = "password";
  }

  @Getter
  @Setter
  public static final class Token {
    private int accessTokenValiditySeconds;
    private int refreshTokenValiditySeconds;
  }
}
