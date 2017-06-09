package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.ScmRepository;
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
    this.url = domain.getUrl().toString();
    this.masterBranch = domain.getMasterBranch();
  }
}
