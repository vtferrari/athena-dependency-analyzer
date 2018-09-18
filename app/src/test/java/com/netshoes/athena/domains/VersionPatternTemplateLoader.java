package com.netshoes.athena.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class VersionPatternTemplateLoader implements TemplateLoader {
  public static final String SPRING_BOOT_STARTER_PARENT_X_X_X_RELEASE =
      "spring-boot-starter-parent:X.X.X.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_X_X_RELEASE =
      "spring-boot-starter-parent:1.X.X.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_2_0_X_RELEASE =
      "spring-boot-starter-parent:2.0.X.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_5_X_RELEASE =
      "spring-boot-starter-parent:1.5.X.RELEASE";
  public static final String SPRING_BOOT_STARTER_PARENT_1_4_X_RELEASE =
      "spring-boot-starter-parent:1.4.X.RELEASE";

  @Override
  public void load() {
    Fixture.of(VersionPattern.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_X_X_X_RELEASE,
            new Rule() {
              {
                add("pattern", "(\\d+).(\\d+).(\\d+).RELEASE");
                add("stable", "2.0.5.RELEASE");
                add(
                    "patterns",
                    has(3)
                        .of(
                            VersionPattern.class,
                            SPRING_BOOT_STARTER_PARENT_2_0_X_RELEASE,
                            SPRING_BOOT_STARTER_PARENT_1_5_X_RELEASE,
                            SPRING_BOOT_STARTER_PARENT_1_4_X_RELEASE));
              }
            });

    Fixture.of(VersionPattern.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_1_X_X_RELEASE,
            new Rule() {
              {
                add("pattern", "1.(\\d+).(\\d+).RELEASE");
                add("stable", "1.5.8.RELEASE");
                add(
                    "patterns",
                    has(2)
                        .of(
                            VersionPattern.class,
                            SPRING_BOOT_STARTER_PARENT_1_5_X_RELEASE,
                            SPRING_BOOT_STARTER_PARENT_1_4_X_RELEASE));
              }
            });

    Fixture.of(VersionPattern.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_1_5_X_RELEASE,
            new Rule() {
              {
                add("pattern", "1.5.(\\d+).RELEASE");
                add("stable", "1.5.8.RELEASE");
              }
            });

    Fixture.of(VersionPattern.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_2_0_X_RELEASE,
            new Rule() {
              {
                add("pattern", "2.0.(\\d+).RELEASE");
                add("stable", "2.0.5.RELEASE");
              }
            });

    Fixture.of(VersionPattern.class)
        .addTemplate(
            SPRING_BOOT_STARTER_PARENT_1_4_X_RELEASE,
            new Rule() {
              {
                add("pattern", "1.4.(\\d+).RELEASE");
                add("stable", "1.4.7.RELEASE");
              }
            });
  }
}
