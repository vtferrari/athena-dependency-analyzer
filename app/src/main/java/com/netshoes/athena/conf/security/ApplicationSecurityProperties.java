package com.netshoes.athena.conf.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("application.security")
public class ApplicationSecurityProperties {

  /** Resource id */
  private String resourceId;

  /** Token configuration */
  private final Token token = new Token();

  /** Username and password for admin user */
  private final User admin = new User();

  @Getter
  @Setter
  public static final class Token {

    /** Validity in seconds for access token */
    private int accessTokenValiditySeconds;

    /** Validity in seconds for refresh token */
    private int refreshTokenValiditySeconds;
  }

  @Getter
  @Setter
  public static final class User {

    /** Username */
    private String username = "admin";
    /** Password */
    private String password = "password";
  }
}
