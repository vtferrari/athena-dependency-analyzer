package com.netshoes.athena.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import java.time.OffsetDateTime;
import java.util.HashMap;

public class ScmApiRateLimitTemplateLoader implements TemplateLoader {
  public static final String REQUESTS_AVAILABLE = "requests_available";
  public static final String REQUESTS_NOT_AVAILABLE = "requests_not_available";

  @Override
  public void load() {
    Fixture.of(ScmApiRateLimit.class)
        .addTemplate(
            REQUESTS_AVAILABLE,
            new Rule() {
              {
                add("resources", new HashMap<>());
                add(
                    "rate",
                    new ScmApiRateLimit.Resource(1000, 900, OffsetDateTime.now().plusHours(1)));
              }
            });

    Fixture.of(ScmApiRateLimit.class)
        .addTemplate(
            REQUESTS_NOT_AVAILABLE,
            new Rule() {
              {
                add("resources", new HashMap<>());
                add(
                    "rate",
                    new ScmApiRateLimit.Resource(1000, 0, OffsetDateTime.now().plusHours(1)));
              }
            });
  }
}
