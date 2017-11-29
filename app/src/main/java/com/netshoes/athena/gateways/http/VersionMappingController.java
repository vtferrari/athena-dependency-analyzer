package com.netshoes.athena.gateways.http;

import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.http.jsons.ErrorJson;
import com.netshoes.athena.gateways.http.jsons.VersionMappingJson;
import com.netshoes.athena.gateways.http.jsons.VersionMappingNotPersistedJson;
import com.netshoes.athena.usecases.CreateVersionMapping;
import com.netshoes.athena.usecases.GetVersionMappings;
import com.netshoes.athena.usecases.UpdateVersionMapping;
import com.netshoes.athena.usecases.exceptions.VersionMappingAlreadyExistsException;
import com.netshoes.athena.usecases.exceptions.VersionMappingNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/versionMappings")
@AllArgsConstructor
@Api(
  value = "/api/v1/versionMappings",
  description = "Operations in Version Mappings",
  tags = "version mappings"
)
public class VersionMappingController {
  private final GetVersionMappings getVersionMappings;
  private final CreateVersionMapping createVersionMapping;
  private final UpdateVersionMapping updateVersionMapping;

  @GetMapping(produces = "application/json")
  @ApiOperation(value = "List version mappings", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(
        code = 200,
        message = "Success",
        response = VersionMappingJson.class,
        responseContainer = "List"
      )
    }
  )
  public List<VersionMappingJson> list() {
    final List<VersionMapping> mappings = getVersionMappings.all();
    return mappings.stream().map(VersionMappingJson::new).collect(Collectors.toList());
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  @ApiOperation(value = "Get version mapping by id", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success", response = VersionMappingJson.class),
      @ApiResponse(code = 404, message = "Version mapping not found", response = ErrorJson.class)
    }
  )
  public VersionMappingJson get(
      @ApiParam(value = "Id of version mapping") @PathVariable("id") String id)
      throws VersionMappingNotFoundException {
    final VersionMapping versionPattern = getVersionMappings.byId(id);
    return new VersionMappingJson(versionPattern);
  }

  @PostMapping(produces = "application/json")
  @ApiOperation(value = "Create a version mapping", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 201, message = "Created", response = VersionMappingJson.class),
      @ApiResponse(
        code = 409,
        message = "Version mapping already exists",
        response = ErrorJson.class
      ),
      @ApiResponse(code = 400, message = "Failure by validation rule", response = ErrorJson.class),
      @ApiResponse(code = 422, message = "Failure by business rule", response = ErrorJson.class)
    }
  )
  public VersionMappingJson create(@Valid @RequestBody VersionMappingNotPersistedJson json)
      throws VersionMappingAlreadyExistsException {
    final VersionMapping versionMapping = createVersionMapping.execute(json.toDomain());
    return new VersionMappingJson(versionMapping);
  }

  @PutMapping(produces = "application/json")
  @ApiOperation(value = "Update a version mapping", produces = "application/json")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Created", response = VersionMappingJson.class),
      @ApiResponse(code = 400, message = "Failure by validation rule", response = ErrorJson.class),
      @ApiResponse(code = 404, message = "Version mapping not found", response = ErrorJson.class),
      @ApiResponse(code = 422, message = "Failure by business rule", response = ErrorJson.class)
    }
  )
  public VersionMappingJson update(@Valid @RequestBody VersionMappingJson json)
      throws VersionMappingNotFoundException {
    final VersionMapping versionMapping = updateVersionMapping.execute(json.toDomain());
    return new VersionMappingJson(versionMapping);
  }
}
