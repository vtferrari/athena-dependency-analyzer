package com.netshoes.athena.gateways.rabbit.jsons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDependenciesAnalyzeRequestJson {

  private String projectId;
}
