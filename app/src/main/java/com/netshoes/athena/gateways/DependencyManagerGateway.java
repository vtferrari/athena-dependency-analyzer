package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.ScmRepositoryContent;
import java.util.List;

public interface DependencyManagerGateway {

  DependencyManagementDescriptor analyze(ScmRepositoryContent content)
      throws InvalidDependencyManagerDescriptorException;

  List<DependencyManagementDescriptor> analyze(List<ScmRepositoryContent> contents)
      throws InvalidDependencyManagerDescriptorException;
}
