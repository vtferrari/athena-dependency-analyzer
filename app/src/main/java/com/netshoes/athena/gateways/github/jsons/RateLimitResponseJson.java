package com.netshoes.athena.gateways.github.jsons;

import com.netshoes.athena.domains.ScmApiRateLimit;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateLimitResponseJson {
  private Resources resources;
  private Resource rate;

  public ScmApiRateLimit toDomain() {
    final Map<String, ScmApiRateLimit.Resource> resourcesMap = new HashMap<>();

    resourcesMap.put("core", resources.getCore().toDomain());
    resourcesMap.put("search", resources.getSearch().toDomain());
    resourcesMap.put("graphql", resources.getGraphql().toDomain());

    final ScmApiRateLimit.Resource rateDomain = rate.toDomain();

    return new ScmApiRateLimit(resourcesMap, rateDomain);
  }

  @Getter
  @Setter
  public static class Resources {
    private Resource core;
    private Resource search;
    private Resource graphql;
  }

  @Getter
  @Setter
  public static class Resource {
    private int limit;
    private int remaining;
    private long reset;

    public ScmApiRateLimit.Resource toDomain() {
      final OffsetDateTime offsetDateTime =
          OffsetDateTime.ofInstant(Instant.ofEpochSecond(reset), ZoneId.of("UTC"));
      return new ScmApiRateLimit.Resource(limit, remaining, offsetDateTime);
    }
  }
}
