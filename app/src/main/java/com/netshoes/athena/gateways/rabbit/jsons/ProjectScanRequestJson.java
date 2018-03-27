package com.netshoes.athena.gateways.rabbit.jsons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectScanRequestJson {

  private String projectId;
  private String repositoryId;
  private String branch;
}
