package com.netshoes.athena.conf;

import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MavenModelConfiguration {

  @Bean
  public MavenXpp3Reader mavenReader() {
    return new MavenXpp3Reader();
  }
}
