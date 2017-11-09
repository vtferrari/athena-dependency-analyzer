package com.netshoes.athena.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScmApiUser {
  private final String login;
  private final String name;
  private final Integer requestLimit;
  private final Integer remainingRequests;
}
