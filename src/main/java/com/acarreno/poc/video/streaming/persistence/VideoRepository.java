package com.acarreno.poc.video.streaming.persistence;

import java.util.LinkedList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, UUID> {

  LinkedList<VideoEntity> findByIdVideo(UUID idVideo);
}
