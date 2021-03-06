package com.netshoes.athena.conf;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfiguration {

  @Bean
  public Docket documentation() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("v1")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(regex("/api/.*"))
        .build()
        .pathMapping("/")
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo());
  }

  @Bean
  public UiConfiguration uiConfig() {
    return new UiConfiguration(
        null,
        "none",
        "alpha",
        "schema",
        UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
        false,
        true,
        60000L);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Athena Dependency Analyzer").version("1.0").build();
  }
}
