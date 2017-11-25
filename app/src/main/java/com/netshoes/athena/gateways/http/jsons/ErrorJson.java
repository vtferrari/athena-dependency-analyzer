package com.netshoes.athena.gateways.http.jsons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "ErrorJson")
public class ErrorJson {

  @ApiModelProperty(value = "Key of error", required = true)
  private String key;

  @ApiModelProperty(value = "Message for error", required = true)
  private String message;

  @ApiModelProperty(value = "Details of error")
  private final Map<String, String> details = new HashMap<>();

  public ErrorJson putDetail(String key, String value) {
    details.put(key, value);
    return this;
  }
}
