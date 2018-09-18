package com.netshoes.athena.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class VersionMappingTemplateLoader implements TemplateLoader {
  public static final String SPRING_BOOT_STARTER_PARENT_1_X_X = "spring-boot-starter-parent-1-x-x";
  public static final String SPRING_BOOT_STARTER_PARENT_2_X_X = "spring-boot-starter-parent-2-x-x";

  @Override
  public void load() {

    Fixture.of(VersionMapping.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_1_X_X,
            new Rule() {
              {
                add("groupId", "org.springframework.boot");
                add("artifactId", "spring-boot-starter-parent");
                add(
                    "patterns",
                    has(1)
                        .of(
                            VersionPattern.class,
                            VersionPatternTemplateLoader.SPRING_BOOT_STARTER_PARENT_1_X_X_RELEASE));
              }
            });

    Fixture.of(VersionMapping.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_2_X_X,
            new Rule() {
              {
                add("groupId", "org.springframework.boot");
                add("artifactId", "spring-boot-starter-parent");
                add(
                    "patterns",
                    has(1)
                        .of(
                            VersionPattern.class,
                            VersionPatternTemplateLoader.SPRING_BOOT_STARTER_PARENT_X_X_X_RELEASE));
              }
            });
  }
}
