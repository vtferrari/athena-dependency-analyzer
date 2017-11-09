package com.netshoes.athena.domains;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
  private final List<T> items;
  private final int totalPages;
  private final long totalItems;
}
