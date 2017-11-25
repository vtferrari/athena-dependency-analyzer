package com.netshoes.athena.gateways.github;

import com.netshoes.athena.conf.GitHubClientProperties;
import com.netshoes.athena.domains.ContentType;
import com.netshoes.athena.domains.ScmApiRateLimit;
import com.netshoes.athena.domains.ScmApiUser;
import com.netshoes.athena.domains.ScmRepository;
import com.netshoes.athena.domains.ScmRepositoryContent;
import com.netshoes.athena.gateways.CouldNotGetRepositoryContentException;
import com.netshoes.athena.gateways.GetRepositoryException;
import com.netshoes.athena.gateways.ScmApiGatewayRateLimitExceededException;
import com.netshoes.athena.gateways.ScmApiGetRateLimitException;
import com.netshoes.athena.gateways.ScmGateway;
import com.netshoes.athena.gateways.github.jsons.RateLimitResponseJson;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.util.EncodingUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class GitHubScmGateway implements ScmGateway {

  private final GitHubClient gitHubClient;
  private final RepositoryService repositoryService;
  private final ContentsService contentsService;
  private final UserService userService;
  private final GitHubClientProperties gitHubClientProperties;

  @Override
  public ScmRepository getRepository(String id) throws GetRepositoryException {
    ScmRepository scmRepository;
    try {
      final RepositoryId repositoryId = RepositoryId.createFromId(id);
      final Repository repository = repositoryService.getRepository(repositoryId);
      scmRepository = convert(repository);
    } catch (Exception e) {
      throw new GetRepositoryException(e);
    }
    return scmRepository;
  }

  @Override
  public List<ScmRepository> getRepositoriesFromConfiguredOrganization()
      throws GetRepositoryException, ScmApiGatewayRateLimitExceededException {
    List<Repository> repositories;

    List<ScmRepository> list = null;
    try {
      repositories = repositoryService.getOrgRepositories(gitHubClientProperties.getOrganization());
      if (repositories != null) {
        list = repositories.stream().map(this::convert).collect(Collectors.toList());
      }
    } catch (RequestException e) {
      ifRateLimitExceededThrowException(e);
      throw new GetRepositoryException(e);
    } catch (Exception e) {
      throw new GetRepositoryException(e);
    }
    log.trace("{} repositories found", list != null ? list.size() : 0);

    return list != null ? list : Collections.EMPTY_LIST;
  }

  private void ifRateLimitExceededThrowException(RequestException requestException)
      throws ScmApiGatewayRateLimitExceededException {
    if (requestException.getMessage().contains("API rate limit exceeded for")) {
      Long minutesToReset = null;
      try {
        final ScmApiRateLimit rateLimit = getRateLimit();
        final OffsetDateTime reset = rateLimit.getRate().getReset();
        minutesToReset = OffsetDateTime.now().until(reset, ChronoUnit.MINUTES);
      } catch (ScmApiGetRateLimitException e) {
        log.warn("Unable to get rate limit from GitHub Api", e);
      }
      throw new ScmApiGatewayRateLimitExceededException(requestException, minutesToReset);
    }
  }

  @Override
  public List<ScmRepositoryContent> getContents(
      ScmRepository repository, String branch, String path)
      throws CouldNotGetRepositoryContentException, ScmApiGatewayRateLimitExceededException {

    final List<ScmRepositoryContent> list = new ArrayList<>();
    List<RepositoryContents> contents;
    try {
      final RepositoryId repositoryId = RepositoryId.createFromId(repository.getId());
      contents = contentsService.getContents(repositoryId, path, branch);

      if (contents != null) {
        contents.forEach(
            repositoryContents -> {
              list.add(convert(repository, repositoryContents));
            });
      }
      log.trace("{} contents found in {} for {}", list.size(), path, repository.getId());

    } catch (RequestException e) {
      ifRateLimitExceededThrowException(e);
      throw new CouldNotGetRepositoryContentException(e);
    } catch (Exception e) {
      throw new CouldNotGetRepositoryContentException(e);
    }
    return list;
  }

  public void retrieveContent(ScmRepositoryContent content)
      throws CouldNotGetRepositoryContentException, ScmApiGatewayRateLimitExceededException {
    final ScmRepository repository = content.getRepository();
    final RepositoryId repositoryId = RepositoryId.createFromId(repository.getId());
    try {
      final String path = content.getPath();

      log.trace("Retrieving content for {} in {} ...", path, repository.getId());

      final List<RepositoryContents> contents = contentsService.getContents(repositoryId, path);

      if (!contents.isEmpty()) {
        final RepositoryContents repositoryContent = contents.get(0);
        content.setSize(repositoryContent.getSize());
        String data = repositoryContent.getContent();
        if (data != null) {
          data = new String(EncodingUtils.fromBase64(data));
        }
        content.setContent(data);
      }
    } catch (RequestException e) {
      ifRateLimitExceededThrowException(e);
      throw new CouldNotGetRepositoryContentException(e);
    } catch (Exception e) {
      throw new CouldNotGetRepositoryContentException(e);
    }
  }

  @Override
  public ScmApiUser getApiUser() throws ScmApiGatewayRateLimitExceededException {
    String name = null;
    String authenticationError = null;
    try {
      final User user = userService.getUser();
      name = user.getName();
    } catch (RequestException e) {
      ifRateLimitExceededThrowException(e);
      log.error(e.getMessage(), e);
    } catch (Exception e) {
      authenticationError = e.getMessage();
      log.error(e.getMessage(), e);
    }
    return new ScmApiUser(
        gitHubClient.getUser(),
        name,
        gitHubClient.getRequestLimit(),
        gitHubClient.getRemainingRequests(),
        authenticationError);
  }

  @Override
  public ScmApiRateLimit getRateLimit() throws ScmApiGetRateLimitException {
    final GitHubRequest request = new GitHubRequest();
    request.setUri("/rate_limit");
    request.setType(RateLimitResponseJson.class);

    ScmApiRateLimit scmApiRateLimit = null;
    try {
      final GitHubResponse gitHubResponse = gitHubClient.get(request);
      final RateLimitResponseJson response = (RateLimitResponseJson) gitHubResponse.getBody();

      if (response != null) {
        scmApiRateLimit = response.toDomain();
      }
    } catch (IOException e) {
      throw new ScmApiGetRateLimitException(e);
    }
    return scmApiRateLimit;
  }

  private ScmRepository convert(Repository repository) {
    ScmRepository scmRepository = null;
    if (repository != null) {
      scmRepository = new ScmRepository();

      scmRepository.setId(repository.generateId());
      scmRepository.setName(repository.getName());
      scmRepository.setDescription(repository.getDescription());
      scmRepository.setMasterBranch(repository.getMasterBranch());
      try {
        scmRepository.setUrl(new URL(repository.getCloneUrl()));
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      }
    }
    return scmRepository;
  }

  private ScmRepositoryContent convert(
      ScmRepository scmRepository, RepositoryContents repositoryContents) {

    final long size = repositoryContents.getSize();
    final String path = repositoryContents.getPath();
    final String name = repositoryContents.getName();
    final ContentType type = ContentType.fromString(repositoryContents.getType());
    String content = repositoryContents.getContent();

    if (content != null) {
      content = new String(EncodingUtils.fromBase64(content));
    }
    return new ScmRepositoryContent(scmRepository, path, name, size, type, content);
  }
}
