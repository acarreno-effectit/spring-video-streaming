package com.acarreno.poc.video.streaming.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.acarreno.poc.video.streaming.persistence.entity.ActionEntity;

public interface ActionRepository extends JpaRepository<ActionEntity, String> {

  @Query(nativeQuery = true,
      value = "SELECT COUNT(*) FROM streaming.action WHERE type=:type AND id_video=:id_video")
  Integer countAction(@Param("type") String type, @Param("id_video") UUID idVideo);

}
