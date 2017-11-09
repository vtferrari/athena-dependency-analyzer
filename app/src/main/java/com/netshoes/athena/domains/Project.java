package com.netshoes.athena.domains;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Base64Utils;

@Data
@EqualsAndHashCode(of = {"scmRepository.id", "branch"})
public class Project {

  private final String id;
  private final String name;
  private final ScmRepository scmRepository;
  private final String branch;
  private final LocalDateTime lastCollectDate;
  private final List<DependencyManagementDescriptor> descriptors = new ArrayList<>();

  public Project(ScmRepository scmRepository, String branch) {
    this.id = generateId(scmRepository, branch);
    this.name = scmRepository.getName();
    this.scmRepository = scmRepository;
    this.branch = branch;
    this.lastCollectDate = null;
  }

  public Project(ScmRepository scmRepository, String branch, LocalDateTime lastCollectDate) {
    this.id = generateId(scmRepository, branch);
    this.name = scmRepository.getName();
    this.scmRepository = scmRepository;
    this.branch = branch;
    this.lastCollectDate = lastCollectDate;
  }

  private String generateId(ScmRepository scmRepository, String branch) {
    final String baseId = MessageFormat.format("{0}${1}", scmRepository.getId(), branch);
    String generateId;
    try {
      generateId = Base64Utils.encodeToString(baseId.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return generateId;
  }

  public void addDependencyManagerDescriptor(DependencyManagementDescriptor descriptor) {
    this.descriptors.add(descriptor);
  }
}
