package com.netshoes.athena;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringCloudApplication
@EnableMongoAuditing
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }
}
