package com.netshoes.athena.conf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class HttpFirewallConfiguration {

  @Bean
  public HttpFirewall httpFirewall() {
    final StrictHttpFirewall httpFirewall = new StrictHttpFirewall();
    httpFirewall.setAllowUrlEncodedPercent(true);
    return httpFirewall;
  }
}
