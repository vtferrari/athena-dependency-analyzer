package com.netshoes.athena.gateways.http.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netshoes.athena.domains.ScmApiRateLimit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "ScmApiRateResourceLimit")
public class ScmApiRateLimitResourceJson {

  @ApiModelProperty(value = "Request limit for configured user", required = true)
  private final Integer limit;

  @ApiModelProperty(value = "Remaining requests for configured user", required = true)
  private final Integer remaining;

  @ApiModelProperty(value = "Next reset for request counter", required = true)
  private final OffsetDateTime reset;

  public ScmApiRateLimitResourceJson(ScmApiRateLimit.Resource domain) {
    this.limit = domain.getLimit();
    this.remaining = domain.getRemaining();
    this.reset = domain.getReset();
  }
}
