package com.acarreno.poc.video.streaming.persistence;

import java.util.LinkedList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acarreno.poc.video.streaming.persistence.entity.ParticipantEntity;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, String> {

  LinkedList<ParticipantEntity> findByVideoIdVideo(UUID idVideo);
}
