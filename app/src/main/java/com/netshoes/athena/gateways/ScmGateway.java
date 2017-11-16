package com.netshoes.athena.gateways;

import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.domains.ScmRepositoryContent;
import java.util.List;

public interface ScmGateway {

  ScmRepository getRepository(String id) throws GetRepositoryException;

  List<ScmRepository> getRepositoriesFromConfiguredOrganization() throws GetRepositoryException;

  List<ScmRepositoryContent> getContents(ScmRepository repository, String branch, String path)
      throws CouldNotGetRepositoryContentException;

  void retrieveContent(ScmRepositoryContent content) throws CouldNotGetRepositoryContentException;

  ScmApiUser getApiUser();
}
