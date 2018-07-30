package com.netshoes.athena.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ScmRepositoryTemplateLoader implements TemplateLoader {
  public static final String DEFAULT = "default";

  @Override
  public void load() {
    Fixture.of(ScmRepository.class)
        .addTemplate(
            DEFAULT,
            new Rule() {
              {
                add("id", "netshoes/default-repository");
                add("name", "default-repository");
                add("masterBranch", "master");
              }
            });
  }
}
