package com.acarreno.poc.video.streaming.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, UUID> {

  Optional<VideoEntity> findByIdVideo(UUID idVideo);
}
