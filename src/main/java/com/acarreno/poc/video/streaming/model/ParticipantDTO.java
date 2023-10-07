package com.acarreno.poc.video.streaming.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantDTO {

  private UUID idParticipant;
  private String firstname;
  private String lastname;
  private ParticipantType type;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

}
