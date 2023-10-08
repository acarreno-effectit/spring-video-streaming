package com.acarreno.poc.video.streaming.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionDTO {

  private UUID idVideo;
  private UUID idAction;
  private String username;
  private ActionType type;
  private String comment;
  private LocalDateTime creationDate;

}
