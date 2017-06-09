package com.netshoes.athena.gateways.rabbit.jsons;

import lombok.Data;

@Data
public class ProjectCollectRequest {

  private final String repositoryId;
  private final String branch;
}
