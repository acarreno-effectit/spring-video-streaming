package com.acarreno.poc.video.streaming.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "streaming", name = "metadata")
public class MetadataEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id_metadata", nullable = false, unique = true)
  private UUID idMetadata;

  @Column(name = "title", nullable = false, unique = false)
  private String title;

  @Column(name = "synopsis", nullable = true, unique = false)
  private String synopsis;

  @Column(name = "year_of_release", nullable = true, unique = false)
  private Integer yearOfRelease;

  @Column(name = "genre", nullable = true, unique = false)
  private String genre;

  @Column(name = "running_time", nullable = true, unique = false)
  private String runningTime;
  
  @CreationTimestamp
  @Column(name = "created_date", nullable = false, unique = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "updated_date", nullable = false, unique = false)
  private LocalDateTime updatedDate;

  @OneToOne
  @JoinColumn(name = "id_video")
  private VideoEntity video;

}
