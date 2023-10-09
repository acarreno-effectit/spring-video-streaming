package com.acarreno.poc.video.streaming.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomError {

  private String message;
  private String description;

}
