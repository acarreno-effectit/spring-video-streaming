package com.acarreno.poc.video.streaming.exception;

import lombok.Getter;

@Getter
public class CustomException extends Exception {

  private static final long serialVersionUID = 5757306658569840746L;

  private String error;

  public CustomException(String message, String error) {
    super(message);
    this.error = error;
  }
}
