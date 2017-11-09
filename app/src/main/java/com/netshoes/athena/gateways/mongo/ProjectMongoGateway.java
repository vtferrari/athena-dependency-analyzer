package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.Project;
import com.netshoes.athena.gateways.ProjectGateway;
import com.netshoes.athena.gateways.mongo.docs.ProjectDoc;
import com.netshoes.athena.gateways.mongo.repositories.ProjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ProjectMongoGateway implements ProjectGateway {

  private final ProjectRepository projectRepository;

  @Override
  public Project findById(String id) {
    final ProjectDoc doc = projectRepository.findOne(id);
    return doc != null ? doc.toDomain(true) : null;
  }

  @Override
  public List<Project> findAll() {
    final List<ProjectDoc> projects = projectRepository.findAll(new Sort(Direction.ASC, "name"));
    return projects.stream().map(p -> p.toDomain(true)).collect(Collectors.toList());
  }

  @Override
  public void save(Project project) {
    final ProjectDoc doc = new ProjectDoc(project);
    projectRepository.save(doc);

    log.debug("Project {} saved.", doc.getId());
  }
}
