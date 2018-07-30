package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.gateways.http.jsons.VersionMappingJson;
import com.netshoes.athena.gateways.http.jsons.VersionMappingNotPersistedJson;
import com.netshoes.athena.usecases.CreateVersionMapping;
import com.netshoes.athena.usecases.DeleteVersionMapping;
import com.netshoes.athena.usecases.GetVersionMappings;
import com.netshoes.athena.usecases.UpdateVersionMapping;
import com.netshoes.athena.usecases.exceptions.VersionMappingNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/versionMappings")
@AllArgsConstructor
@Api(
    value = "/api/v1/versionMappings",
    description = "Operations in Version Mappings",
    tags = "version mappings")
public class VersionMappingsController {
  private final GetVersionMappings getVersionMappings;
  private final CreateVersionMapping createVersionMapping;
  private final UpdateVersionMapping updateVersionMapping;
  private final DeleteVersionMapping deleteVersionMapping;

  @GetMapping(produces = "application/json")
  @ApiOperation(value = "List version mappings", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Success",
            response = VersionMappingJson.class,
            responseContainer = "List")
      })
  public Flux<VersionMappingJson> list() {
    final Flux<VersionMapping> mappings = getVersionMappings.all();
    return mappings.map(VersionMappingJson::new);
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  @ApiOperation(value = "Get version mapping by id", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = VersionMappingJson.class),
        @ApiResponse(code = 404, message = "Version mapping not found", response = ErrorJson.class)
      })
  public Mono<VersionMappingJson> get(
      @ApiParam(value = "Id of version mapping") @PathVariable("id") String id) {
    final Mono<VersionMapping> versionPattern = getVersionMappings.byId(id);
    return versionPattern.map(VersionMappingJson::new);
  }

  @PostMapping(produces = "application/json")
  @ApiOperation(value = "Create a version mapping", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created", response = VersionMappingJson.class),
        @ApiResponse(
            code = 409,
            message = "Version mapping already exists",
            response = ErrorJson.class),
        @ApiResponse(
            code = 400,
            message = "Failure by validation rule",
            response = ErrorJson.class),
        @ApiResponse(code = 422, message = "Failure by business rule", response = ErrorJson.class)
      })
  public Mono<VersionMappingJson> create(
      @Valid @RequestBody VersionMappingNotPersistedJson json,
      @RequestHeader String authorization) {
    return Mono.just(json)
        .map(VersionMappingNotPersistedJson::toDomain)
        .flatMap(createVersionMapping::execute)
        .map(VersionMappingJson::new);
  }

  @PutMapping(produces = "application/json")
  @ApiOperation(value = "Update a version mapping", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Created", response = VersionMappingJson.class),
        @ApiResponse(
            code = 400,
            message = "Failure by validation rule",
            response = ErrorJson.class),
        @ApiResponse(code = 404, message = "Version mapping not found", response = ErrorJson.class),
        @ApiResponse(code = 422, message = "Failure by business rule", response = ErrorJson.class)
      })
  public Mono<VersionMappingJson> update(
      @Valid @RequestBody VersionMappingJson json, @RequestHeader String authorization) {
    return Mono.just(json)
        .map(VersionMappingNotPersistedJson::toDomain)
        .flatMap(updateVersionMapping::execute)
        .map(VersionMappingJson::new);
  }

  @DeleteMapping(path = "/{id}", produces = "application/json")
  @ApiOperation(value = "Delete version mapping", produces = "application/json")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = VersionMappingJson.class),
        @ApiResponse(code = 404, message = "Version mapping not found", response = ErrorJson.class)
      })
  public Mono<Void> delete(
      @ApiParam(value = "Id of version mapping") @PathVariable("id") String id,
      @RequestHeader String authorization)
      throws VersionMappingNotFoundException {
    return deleteVersionMapping.byId(id);
  }
}
