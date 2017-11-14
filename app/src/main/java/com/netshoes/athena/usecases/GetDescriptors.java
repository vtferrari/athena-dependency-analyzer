package com.netshoes.athena.usecases;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.usecases.exceptions.DescriptorNotFoundException;
import com.netshoes.athena.usecases.exceptions.ProjectNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetDescriptors {

  private final GetProjects getProjects;

  public List<DependencyManagementDescriptor> byProject(String projectId)
      throws ProjectNotFoundException {
    final Project project = getProjects.byId(projectId);
    return project.getDescriptors();
  }

  public DependencyManagementDescriptor byId(String projectId, String descriptorId)
      throws ProjectNotFoundException, DescriptorNotFoundException {
    final Project project = getProjects.byId(projectId);
    final List<DependencyManagementDescriptor> descriptors = project.getDescriptors();

    final Optional<DependencyManagementDescriptor> descriptor =
        descriptors
            .stream()
            .filter(d -> descriptorId.equals(d.getDependencyDescriptorId()))
            .findFirst();

    if (!descriptor.isPresent()) {
      throw new DescriptorNotFoundException(descriptorId);
    }
    return descriptor.get();
  }
}
