package com.acarreno.poc.video.streaming.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoDTO {

  private String filename;
  private String content;

}
