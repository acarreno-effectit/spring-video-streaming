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
import jakarta.persistence.ManyToOne;
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
@Table(schema = "streaming", name = "participant")
public class ParticipantEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id_participant", nullable = false, unique = true)
  private UUID idParticipant;

  @Column(name = "firstname", nullable = false, unique = false)
  private String firstname;

  @Column(name = "lastname", nullable = true, unique = false)
  private String lastname;

  @Column(name = "type", nullable = true, unique = false)
  private String type;
  
  @CreationTimestamp
  @Column(name = "created_date", nullable = false, unique = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "updated_date", nullable = false, unique = false)
  private LocalDateTime updatedDate;

  @ManyToOne
  @JoinColumn(name = "id_video")
  private VideoEntity video;

}
