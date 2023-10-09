package com.acarreno.poc.video.streaming.controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.acarreno.poc.video.streaming.exception.CustomException;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ResponseDTO;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "metadata", description = "Metadata Controller")
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
public class MetadataController {

  private final VideoStreamingService videoStreamingService;

  @Operation(summary = "Save Metadata", description = "API to save Metadata", tags = {"metadata"})
  @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO> save(@RequestBody MetadataDTO metadata) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(videoStreamingService.saveMetadata(metadata));
  }

  @Operation(summary = "Update Metadata", description = "API to save Metadata", tags = {"metadata"})
  @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO> update(@RequestBody MetadataDTO metadata)
      throws CustomException {
    return ResponseEntity.status(HttpStatus.OK)
        .body(videoStreamingService.updateMetadata(metadata));
  }

  @Operation(summary = "Get Metadata By ID Video", description = "API to get Metadata By ID Video",
      tags = {"metadata"})
  @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @GetMapping(value = "/{idVideo}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<MetadataDTO> get(@PathVariable UUID idVideo) throws CustomException {
    return ResponseEntity.status(HttpStatus.OK)
        .body(videoStreamingService.getMetadataByIdVideo(idVideo));
  }

}
