package com.acarreno.poc.video.streaming.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.acarreno.poc.video.streaming.model.ActionDTO;
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
@Tag(name = "action", description = "Action Controller")
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActionController {

  private final VideoStreamingService videoStreamingService;

  @Operation(summary = "Save Action", description = "API to save Action", tags = {"action"})
  @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PostMapping(value = "/action", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO> save(@RequestBody ActionDTO action) {
    return ResponseEntity.status(HttpStatus.CREATED).body(videoStreamingService.saveAction(action));
  }

}
