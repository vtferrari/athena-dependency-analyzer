package com.netshoes.athena.conf.security;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
  private final ApplicationSecurityProperties applicationSecurityProperties;
  private final DefaultTokenServices tokenServices;
  private final TokenStore tokenStore;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources
        .resourceId(applicationSecurityProperties.getResourceId())
        .tokenServices(tokenServices)
        .tokenStore(tokenStore);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.requestMatcher(new OAuthRequestedMatcher())
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/api/v1/projects/scan",
            "/api/v1/projects/*/refresh",
            "/api/v1/projects/*/refreshNow",
            "/api/v1/projects/dependencies/analyze",
            "/api/v1/projects/*/dependencies/analyzeNow",
            "/api/v1/scms/**")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/api/v1/versionMappings/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/v1/versionMappings/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/v1/versionMappings/*")
        .hasRole("ADMIN")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    ;
  }

  private static class OAuthRequestedMatcher implements RequestMatcher {
    public boolean matches(HttpServletRequest request) {
      String path = request.getServletPath();
      if (path.length() >= 5) {
        path = path.substring(0, 5);
        boolean isApi = path.equals("/api/");
        return isApi;
      } else return false;
    }
  }
}
