package com.netshoes.athena.gateways.maven;

import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactOrigin;
import com.netshoes.athena.domains.DependencyArtifact;
import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.DependencyScope;
import com.netshoes.athena.domains.MavenDependencyManagementDescriptor;
import com.netshoes.athena.domains.ScmRepositoryContent;
import com.netshoes.athena.gateways.DependencyManagerGateway;
import com.netshoes.athena.gateways.InvalidDependencyManagerDescriptorException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.stereotype.Component;

@Component
public class MavenDependencyManagerGateway implements DependencyManagerGateway {

  @Override
  public List<DependencyManagementDescriptor> analyze(List<ScmRepositoryContent> contents)
      throws InvalidDependencyManagerDescriptorException {

    final List<DependencyManagementDescriptor> list = new ArrayList<>();
    for (ScmRepositoryContent content : contents) {
      list.add(analyze(content));
    }
    return list;
  }

  @Override
  public DependencyManagementDescriptor analyze(ScmRepositoryContent content)
      throws InvalidDependencyManagerDescriptorException {

    DependencyManagementDescriptor descriptor;
    final MavenXpp3Reader reader = new MavenXpp3Reader();
    try {
      final Model model = reader.read(new StringReader(content.getContent()));
      descriptor = build(model);
    } catch (Exception e) {
      throw new InvalidDependencyManagerDescriptorException(e);
    }
    return descriptor;
  }

  private MavenDependencyManagementDescriptor build(Model model) {
    final Parent parent = model.getParent();
    Artifact parentArtifact = null;
    Artifact project;
    if (parent != null) {
      parentArtifact =
          Artifact.ofParent(parent.getGroupId(), parent.getArtifactId(), parent.getVersion());
      project =
          Artifact.ofProject(
              model.getGroupId(), model.getArtifactId(), model.getVersion(), parentArtifact);
    } else {
      project = Artifact.ofProject(model.getGroupId(), model.getArtifactId(), model.getVersion());
    }

    final MavenDependencyManagementDescriptor descriptor =
        new MavenDependencyManagementDescriptor(project);

    descriptor.setParentArtifact(Optional.ofNullable(parentArtifact));

    final Properties properties = model.getProperties();

    final List<DependencyArtifact> dependencyArtifacts =
        convertDependencies(model.getDependencies(), properties, ArtifactOrigin.DEPENDENCIES);

    descriptor.setDependencyArtifacts(dependencyArtifacts);

    final DependencyManagement dependencyManagement = model.getDependencyManagement();
    if (dependencyManagement != null) {
      final List<DependencyArtifact> dependencyManagementArtifacts =
          convertDependencies(
              dependencyManagement.getDependencies(),
              properties,
              ArtifactOrigin.DEPENDENCIES_MANAGEMENT);
      descriptor.setDependencyManagementArtifacts(dependencyManagementArtifacts);
    }
    return descriptor;
  }

  public List<DependencyArtifact> convertDependencies(
      List<Dependency> dependencies, Properties properties, ArtifactOrigin artifactOrigin) {
    final List<DependencyArtifact> dependencyArtifacts =
        dependencies
            .stream()
            .map(
                dependency -> {
                  final String dependencyVersion = dependency.getVersion();
                  String version;
                  if (dependencyVersion == null) {
                    version = "managed";
                  } else {
                    version = replacePropertyIfNecessary(dependency.getVersion(), properties);
                  }
                  final String scope = dependency.getScope();
                  final DependencyScope dependencyScope =
                      scope == null ? DependencyScope.MANAGED : DependencyScope.fromString(scope);
                  final DependencyArtifact dependencyArtifact =
                      new DependencyArtifact(
                          dependency.getGroupId(),
                          dependency.getArtifactId(),
                          version,
                          dependencyScope,
                          artifactOrigin);
                  return dependencyArtifact;
                })
            .collect(Collectors.toList());
    return dependencyArtifacts;
  }

  public String replacePropertyIfNecessary(String value, Properties properties) {
    if (value.startsWith("${")) {
      final String propertyBinding = value.replaceFirst("\\$\\{", "").replaceFirst("}", "");
      value = (String) properties.get(propertyBinding);
    }
    return value;
  }
}
