package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.DependencyManagementDescriptor;
import com.netshoes.athena.domains.Project;
import com.netshoes.athena.domains.ScmRepository;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
@TypeAlias("project")
@CompoundIndexes(
  value = {
    @CompoundIndex(
      name = "ix_artifacts",
      def =
          "{'descriptors.artifacts.groupId':1,"
              + "'descriptors.artifacts.artifactId':1,"
              + "'descriptors.artifacts.version':1}"
    )
  }
)
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class ProjectDoc implements Serializable {

  @Id private String id;
  private String branch;
  private ScmRepositoryDoc scmRepository;
  private List<DependencyManagementDescriptorDoc> descriptors;

  @LastModifiedDate private Date lastCollectDate;

  public ProjectDoc(Project domain) {
    final ScmRepository domainScmRepository = domain.getScmRepository();
    this.id = domain.getId();
    this.branch = domain.getBranch();

    final List<DependencyManagementDescriptor> domainDescriptors = domain.getDescriptors();
    this.descriptors =
        domainDescriptors
            .stream()
            .map(DependencyManagementDescriptorDoc::new)
            .collect(Collectors.toList());
    this.scmRepository = new ScmRepositoryDoc(domainScmRepository);
  }

  public Project toDomain() {
    final ScmRepositoryDoc scmRepositoryDoc = scmRepository;

    final ScmRepository scmRepositoryDomain = new ScmRepository();
    scmRepositoryDomain.setId(scmRepositoryDoc.getId());
    scmRepositoryDomain.setName(scmRepositoryDoc.getName());
    scmRepositoryDomain.setDescription(scmRepositoryDoc.getDescription());
    scmRepositoryDomain.setMasterBranch(scmRepositoryDoc.getMasterBranch());
    try {
      scmRepositoryDomain.setUrl(new URL(scmRepositoryDoc.getUrl()));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    return new Project(scmRepositoryDomain, branch);
  }
}
