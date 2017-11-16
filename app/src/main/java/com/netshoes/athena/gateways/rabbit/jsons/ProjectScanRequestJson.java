package com.netshoes.athena.gateways.rabbit.jsons;

import lombok.Data;

@Data
public class ProjectScanRequestJson {

  private final String projectId;
  private final String repositoryId;
  private final String branch;
}
