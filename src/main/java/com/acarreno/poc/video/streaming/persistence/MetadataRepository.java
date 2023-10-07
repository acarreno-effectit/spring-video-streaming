package com.acarreno.poc.video.streaming.persistence;

import java.util.LinkedList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.acarreno.poc.video.streaming.persistence.entity.MetadataEntity;

public interface MetadataRepository extends JpaRepository<MetadataEntity, String> {

  LinkedList<MetadataEntity> findByIdMetadata(UUID idMetadata);

  LinkedList<MetadataEntity> findByVideoIdVideo(UUID idVideo);

  @Query(nativeQuery = true,
      value = "SELECT ME.* FROM streaming.metadata ME JOIN streaming.video VI ON ME.id_video = VI.id_video WHERE VI.status = 'ACTIVED'")
  LinkedList<MetadataEntity> findByActiveVideo();

}
