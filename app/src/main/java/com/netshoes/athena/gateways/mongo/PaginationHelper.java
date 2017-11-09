package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.PaginatedResponse;
import com.netshoes.athena.domains.RequestOfPage;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationHelper {

  public <T, R> PaginatedResponse<R> createResponse(
      Page<T> page, Function<? super T, ? extends R> mapper) {
    final List<T> pageContent = page.getContent();
    final List<R> list = pageContent.stream().map(mapper).collect(Collectors.toList());

    return new PaginatedResponse<>(list, page.getTotalPages(), page.getTotalElements());
  }

  public PageRequest createRequest(RequestOfPage requestOfPage) {
    return new PageRequest(requestOfPage.getPageNumber(), requestOfPage.getPageSize());
  }

  public PageRequest createRequest(RequestOfPage requestOfPagee, Sort sort) {
    return new PageRequest(requestOfPagee.getPageNumber(), requestOfPagee.getPageSize(), sort);
  }
}
