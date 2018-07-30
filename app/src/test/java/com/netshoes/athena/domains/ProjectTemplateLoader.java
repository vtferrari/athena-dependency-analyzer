package com.netshoes.athena.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ProjectTemplateLoader implements TemplateLoader {
  public static final String DEFAULT = "default";

  @Override
  public void load() {
    Fixture.of(Project.class)
        .addTemplate(
            DEFAULT,
            new Rule() {
              {
                add("scmRepository", one(ScmRepository.class, ScmRepositoryTemplateLoader.DEFAULT));
                add("branch", "master");
              }
            });
  }
}
