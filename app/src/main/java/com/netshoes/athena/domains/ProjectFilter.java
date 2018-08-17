package com.netshoes.athena.domains;

import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProjectFilter {
  private final Optional<String> name;
  private final boolean onlyWithDependencyManager;
}
