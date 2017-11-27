package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.ScmRepository;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScmRepositoryDoc {

  private String id;
  private String name;
  private String description;
  private String url;
  private String masterBranch;

  public ScmRepositoryDoc(ScmRepository domain) {
    this.id = domain.getId();
    this.name = domain.getName();
    this.description = domain.getDescription();
    this.url = domain.getUrl() != null ? domain.getUrl().toString() : null;
    this.masterBranch = domain.getMasterBranch();
  }

  public ScmRepository toDomain() {
    final ScmRepository domain = new ScmRepository();
    domain.setId(id);
    domain.setName(name);
    domain.setDescription(description);
    domain.setMasterBranch(masterBranch);
    if (url != null) {
      try {
        domain.setUrl(new URL(url));
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      }
    }
    return domain;
  }
}
