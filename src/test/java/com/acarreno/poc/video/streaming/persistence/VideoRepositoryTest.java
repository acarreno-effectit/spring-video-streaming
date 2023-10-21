package com.acarreno.poc.video.streaming.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VideoRepositoryTest {
  
  private UUID idVideo;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private VideoRepository repository;
  
  @BeforeEach
  void setUp() {
    VideoEntity entity = VideoEntity.builder().filename("filename.mp4").content("video")
        .status(StatusVideoType.LOADED.name()).build();

    entity = entityManager.persist(entity);
    this.idVideo = entity.getIdVideo();
  }

  @Test
  public void saveParticipantSuccessful() {
    VideoEntity entity = VideoEntity.builder().filename("filename.mp4").content("video")
        .status(StatusVideoType.LOADED.name()).build();

    entity = repository.save(entity);
    assertThat(entity).hasFieldOrProperty("idVideo");
  }

  @Test
  public void findParticipantsByIdVideoSuccessful() {
    Optional<VideoEntity> entity = repository.findByIdVideo(idVideo);
    assertTrue(entity.isPresent());
  }

  @Test
  public void findParticipantsByIdVideoEmpty() {
    Optional<VideoEntity> entity = repository.findByIdVideo(UUID.randomUUID());
    assertTrue(entity.isEmpty());
  }


}
