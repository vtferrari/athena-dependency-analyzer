package com.netshoes.athena.conf.security;

import com.netshoes.athena.conf.security.ApplicationSecurityProperties.Token;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(ApplicationSecurityProperties.class)
@AllArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
  private static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";
  private static final String SIGNING_KEY = "athena";
  private final AuthenticationManager authenticationManager;
  private final OAuth2ClientProperties oAuth2ClientProperties;
  private final ApplicationSecurityProperties applicationSecurityProperties;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .authenticationManager(authenticationManager)
        .tokenServices(tokenServices())
        .tokenStore(tokenStore())
        .accessTokenConverter(accessTokenConverter());
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(SIGNING_KEY);
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final Token token = applicationSecurityProperties.getToken();
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setAccessTokenValiditySeconds(token.getAccessTokenValiditySeconds());
    defaultTokenServices.setRefreshTokenValiditySeconds(token.getRefreshTokenValiditySeconds());
    defaultTokenServices.setSupportRefreshToken(true);
    defaultTokenServices.setTokenEnhancer(accessTokenConverter());
    return defaultTokenServices;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer
        .tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
        .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    final Token token = applicationSecurityProperties.getToken();
    clients
        .inMemory()
        .withClient(oAuth2ClientProperties.getClientId())
        .secret(oAuth2ClientProperties.getClientSecret())
        .authorizedGrantTypes("password", "refresh_token")
        .authorities(ROLE_TRUSTED_CLIENT)
        .scopes("read", "write")
        .resourceIds(applicationSecurityProperties.getResourceId())
        .accessTokenValiditySeconds(token.getAccessTokenValiditySeconds())
        .refreshTokenValiditySeconds(token.getRefreshTokenValiditySeconds());
  }
}
