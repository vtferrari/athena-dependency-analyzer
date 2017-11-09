package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProjects {

  private final ProjectGateway projectGateway;

  public List<Project> all() {
    return projectGateway.findAll();
  }

  public Project byId(String id) throws ProjectNotFoundException {
    final Project project = projectGateway.findById(id);
    if (project == null) {
      throw new ProjectNotFoundException(id);
    }

    return project;
  }
}
