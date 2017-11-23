package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.PaginatedResponse;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.RequestOfPage;

public interface ProjectGateway {

  Project findById(String id);

  PaginatedResponse<Project> findAll(RequestOfPage requestOfPage);

  PaginatedResponse<Project> findByNameContaining(RequestOfPage requestOfPage, String name);

  Project save(Project project);
}
