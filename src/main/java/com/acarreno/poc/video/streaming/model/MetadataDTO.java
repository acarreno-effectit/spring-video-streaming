package com.acarreno.poc.video.streaming.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetadataDTO {

  private UUID idVideo;
  private UUID idMetadata;
  private String title;
  private String synopsis;
  private Integer yearOfRelease;
  private String genre;
  private String runningTime;
  private LinkedList<ParticipantDTO> participants;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

}
