package com.netshoes.athena.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestOfPage {
  private final int pageNumber;
  private final int pageSize;
}
