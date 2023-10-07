package com.acarreno.poc.video.streaming.persistence.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "streaming", name = "video")
public class VideoEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id_video", nullable = false, unique = true)
  private UUID idVideo;

  @Column(name = "filename", nullable = false, unique = true)
  private String filename;

  @Column(name = "content", nullable = true, unique = false)
  private byte[] content;

  @Column(name = "size_in_bytes", nullable = true, unique = false)
  private long sizeInBytes;

  @Column(name = "status", nullable = false, unique = true)
  private String status;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, unique = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "updated_date", nullable = false, unique = false)
  private LocalDateTime updatedDate;

  @OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
  private MetadataEntity metadata;

  @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
  private Set<ParticipantEntity> participants;

  @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
  private Set<ActionEntity> actions;
}
