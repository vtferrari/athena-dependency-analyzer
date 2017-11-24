package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.ScmApiRateLimit;
import com.netshoes.athena.domains.ScmApiRateLimit.Resource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "ScmApiRateLimit")
public class ScmApiRateLimitJson {

  @ApiModelProperty(value = "Limit for each kind of resource", required = true)
  private final Map<String, ScmApiRateLimitResourceJson> resources;

  @ApiModelProperty(value = "Limit rate", required = true)
  private final ScmApiRateLimitResourceJson rate;

  public ScmApiRateLimitJson(ScmApiRateLimit domain) {
    this.rate = new ScmApiRateLimitResourceJson(domain.getRate());
    this.resources = new HashMap<>();
    final Map<String, Resource> domainResources = domain.getResources();

    domainResources.forEach(
        (String k, Resource v) -> resources.put(k, new ScmApiRateLimitResourceJson(v)));
  }
}
