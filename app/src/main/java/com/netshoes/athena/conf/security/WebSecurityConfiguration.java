package com.netshoes.athena.conf.security;

import com.netshoes.athena.conf.security.ApplicationSecurityProperties.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableConfigurationProperties(ApplicationSecurityProperties.class)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final ApplicationSecurityProperties securityProperties;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/health")
        .antMatchers("/info")
        .antMatchers("/env")
        .antMatchers("/metrics")
        .antMatchers("/swagger-ui.html")
        .antMatchers("/swagger-resources/**")
        .antMatchers("/webjars/**")
        .antMatchers("/v2/api-docs");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()
        .disable()
        .logout()
        .disable()
        .anonymous()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    final User adminUser = securityProperties.getAdmin();
    auth.inMemoryAuthentication()
        .withUser(adminUser.getUsername())
        .password(adminUser.getPassword())
        .roles("ADMIN");
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
