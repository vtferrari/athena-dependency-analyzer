package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.Project;
import java.util.List;

public interface ProjectGateway {

  Project findById(String id);

  List<Project> findAll();

  void save(Project project);
}
