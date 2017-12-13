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
import java.util.function.Consumer;
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

  private Artifact buildParentArtifact(Parent parent) {
    Artifact parentArtifact = null;
    if (parent != null) {
      final String parentGroupId = parent.getGroupId();
      final String parentArtifactId = parent.getArtifactId();
      final String parentVersion = parent.getVersion();

      parentArtifact = Artifact.ofParent(parentGroupId, parentArtifactId, parentVersion);
    }
    return parentArtifact;
  }

  private Artifact buildProjectArtifact(Model model, Artifact parentArtifact) {
    Artifact project;
    final String groupId = model.getGroupId();
    final String artifactId = model.getArtifactId();
    final String version = model.getVersion();
    if (parentArtifact != null) {
      project = Artifact.ofProject(groupId, artifactId, version, parentArtifact);
    } else {
      project = Artifact.ofProject(groupId, artifactId, version);
    }
    return project;
  }

  private MavenDependencyManagementDescriptor build(Model model) {
    final Parent parent = model.getParent();
    final Artifact parentArtifact = buildParentArtifact(parent);
    final Artifact project = buildProjectArtifact(model, parentArtifact);

    final MavenDependencyManagementDescriptor descriptor =
        new MavenDependencyManagementDescriptor(project);

    descriptor.setParentArtifact(Optional.ofNullable(parentArtifact));

    final Properties properties = model.getProperties();

    convertDependencies(
        model.getDependencies(),
        properties,
        ArtifactOrigin.DEPENDENCIES,
        descriptor::addDependencyArtifact);

    final DependencyManagement dependencyManagement = model.getDependencyManagement();
    if (dependencyManagement != null) {

      convertDependencies(
          dependencyManagement.getDependencies(),
          properties,
          ArtifactOrigin.DEPENDENCIES_MANAGEMENT,
          descriptor::addDependencyManagementArtifact);
    }
    return descriptor;
  }

  public void convertDependencies(
      List<Dependency> dependencies,
      Properties properties,
      ArtifactOrigin artifactOrigin,
      Consumer<DependencyArtifact> consumer) {
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
              final String groupId = dependency.getGroupId();
              final String artifactId = dependency.getArtifactId();
              final DependencyArtifact dependencyArtifact =
                  new DependencyArtifact(
                      groupId, artifactId, version, dependencyScope, artifactOrigin);
              return dependencyArtifact;
            })
        .forEach(consumer);
  }

  public String replacePropertyIfNecessary(String value, Properties properties) {
    if (value.startsWith("${")) {
      final String propertyBinding = value.replaceFirst("\\$\\{", "").replaceFirst("}", "");
      value = (String) properties.get(propertyBinding);
    }
    return value;
  }
}
