package com.acarreno.poc.video.streaming.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ResponseDTO;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "video", description = "Video Controller")
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

  private final VideoStreamingService videoStreamingService;

  @Operation(summary = "Load Video", description = "API to load Video", tags = {"video"})
  @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO> load(@RequestParam MultipartFile video) throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(videoStreamingService.loadVideo(video.getResource()));
  }

  @Operation(summary = "Change Status Video", description = "API to Change Status Video",
      tags = {"video"})
  @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PatchMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO> statusVideo(@RequestParam(name = "idVideo") UUID idVideo,
      @RequestParam(name = "status") StatusVideoType status) throws IOException {
    return ResponseEntity.status(HttpStatus.OK)
        .body(videoStreamingService.changeStatusVideo(idVideo, status));
  }


  @Operation(summary = "Get Basic Videos Info", description = "API to get Basic Videos Info",
      tags = {"video"})
  @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<LinkedList<MetadataDTO>> get() throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED).body(videoStreamingService.getVideosInfo());
  }

}
