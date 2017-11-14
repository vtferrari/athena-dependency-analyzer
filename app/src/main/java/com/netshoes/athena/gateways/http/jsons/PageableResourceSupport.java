package com.netshoes.athena.gateways.http.jsons;

import com.netshoes.athena.domains.PaginatedResponse;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

public class PageableResourceSupport<T, R> extends ResourceSupport {

  @ApiModelProperty(value = "List of items")
  @Getter
  private List<R> items;

  @ApiModelProperty(value = "Page number", required = true)
  @Getter
  private int pageNumber;

  @ApiModelProperty(value = "Page size", required = true)
  @Getter
  private int pageSize;

  @ApiModelProperty(value = "Total of pages", required = true)
  @Getter
  private int totalPages;

  @ApiModelProperty(value = "Total of elements", required = true)
  @Getter
  private long totalItems;

  public void initialize(
      PaginatedResponse<T> paginatedResponse,
      Function<? super T, ? extends R> mapper,
      int pageNumber,
      int pageSize) {

    this.items = paginatedResponse.getItems().stream().map(mapper).collect(Collectors.toList());
    this.totalPages = paginatedResponse.getTotalPages();
    this.totalItems = paginatedResponse.getTotalItems();
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
  }
}
