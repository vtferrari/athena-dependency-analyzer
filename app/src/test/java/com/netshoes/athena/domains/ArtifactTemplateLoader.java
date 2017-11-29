package com.netshoes.athena.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ArtifactTemplateLoader implements TemplateLoader {
  private static final String SPRING_BOOT_STARTER_PARENT = "spring-boot-starter-parent";

  public static final String SPRING_BOOT_STARTER_PARENT_1_5_8_RELEASE =
      "spring-boot-starter-parent-1.5.8.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_5_7_RELEASE =
      "spring-boot-starter-parent-1.5.7.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_4_7_RELEASE =
      "spring-boot-starter-parent-1.4.7.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_4_5_RELEASE =
      "spring-boot-starter-parent-1.4.0.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_3_2_RELEASE =
      "spring-boot-starter-parent-1.3.2.RELEASE";

  @Override
  public void load() {
    Fixture.of(Artifact.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT,
            new Rule() {
              {
                add("groupId", "org.springframework.boot");
                add("artifactId", "spring-boot-starter-parent");
                add("origin", ArtifactOrigin.PARENT);
              }
            });

    Fixture.of(Artifact.class)
        .addTemplate(SPRING_BOOT_STARTER_PARENT_1_5_8_RELEASE)
        .inherits(
            SPRING_BOOT_STARTER_PARENT,
            new Rule() {
              {
                add("groupId", "org.springframework.boot");
                add("artifactId", "spring-boot-starter-parent");
                add("version", "1.5.8.RELEASE");
                add("origin", ArtifactOrigin.PARENT);
              }
            });
    Fixture.of(Artifact.class)
        .addTemplate(SPRING_BOOT_STARTER_PARENT_1_5_7_RELEASE)
        .inherits(
            SPRING_BOOT_STARTER_PARENT,
            new Rule() {
              {
                add("version", "1.5.7.RELEASE");
              }
            });
    Fixture.of(Artifact.class)
        .addTemplate(SPRING_BOOT_STARTER_PARENT_1_4_7_RELEASE)
        .inherits(
            SPRING_BOOT_STARTER_PARENT,
            new Rule() {
              {
                add("version", "1.4.7.RELEASE");
              }
            });
    Fixture.of(Artifact.class)
        .addTemplate(SPRING_BOOT_STARTER_PARENT_1_4_5_RELEASE)
        .inherits(
            SPRING_BOOT_STARTER_PARENT,
            new Rule() {
              {
                add("version", "1.4.5.RELEASE");
              }
            });
    Fixture.of(Artifact.class)
        .addTemplate(SPRING_BOOT_STARTER_PARENT_1_3_2_RELEASE)
        .inherits(
            SPRING_BOOT_STARTER_PARENT,
            new Rule() {
              {
                add("version", "1.3.2.RELEASE");
              }
            });
  }
}
