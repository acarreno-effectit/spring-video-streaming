package com.acarreno.poc.video.streaming.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticDTO {

  private Integer views;
  private Integer comments;
  private Integer loads;

}
