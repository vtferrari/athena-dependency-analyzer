package com.netshoes.athena.domains;

import java.time.OffsetDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScmApiRateLimit {
  private final Map<String, Resource> resources;
  private final Resource rate;

  @Getter
  @Setter
  @AllArgsConstructor
  public static class Resource {
    private int limit;
    private int remaining;
    private OffsetDateTime reset;
  }
}
