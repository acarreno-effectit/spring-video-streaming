package com.acarreno.poc.video.streaming.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.acarreno.poc.video.streaming.model.GenreType;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.persistence.entity.MetadataEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MetadataRepositoryTest {

  private UUID idVideo;
  private UUID idMetadata;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private MetadataRepository repository;

  @BeforeEach
  void setUp() {

    VideoEntity entity = VideoEntity.builder().filename("filename.mp4").content("video".getBytes())
        .status(StatusVideoType.ACTIVED.name()).build();

    entity = entityManager.persist(entity);
    this.idVideo = entity.getIdVideo();

    MetadataEntity entity1 =
        MetadataEntity.builder().video(VideoEntity.builder().idVideo(entity.getIdVideo()).build())
            .genre(GenreType.ACTION.name()).runningTime("2h 30m").synopsis("Synopsis test")
            .createdDate(LocalDateTime.now()).title("My Title").yearOfRelease(2023).build();

    entity1 = entityManager.persist(entity1);
    this.idMetadata = entity1.getIdMetadata();

  }

  @Test
  public void saveMetadataSuccessful() {
    MetadataEntity entity =
        MetadataEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .genre(GenreType.ACTION.name()).runningTime("2h 30m").synopsis("Synopsis test")
            .createdDate(LocalDateTime.now()).title("My Title").yearOfRelease(2023).build();

    entity = repository.save(entity);
    assertThat(entity).hasFieldOrProperty("idMetadata");
  }

  @Test
  public void findMetadataByIdMetadataSuccessful() {
    Optional<MetadataEntity> entity = repository.findByIdMetadata(this.idMetadata);
    assertTrue(entity.isPresent());
  }

  @Test
  public void findByIdMetadataEmpty() {
    Optional<MetadataEntity> entity = repository.findByIdMetadata(UUID.randomUUID());
    assertTrue(entity.isEmpty());
  }

  @Test
  public void findMetadataByIdVideoSuccessful() {
    Optional<MetadataEntity> entity = repository.findByVideoIdVideo(this.idVideo);
    assertTrue(entity.isPresent());
  }

  @Test
  public void findMetadataByIdVideoEmpty() {
    Optional<MetadataEntity> entity = repository.findByVideoIdVideo(UUID.randomUUID());
    assertTrue(entity.isEmpty());
  }

  @Test
  public void findMetadataByIdActiveVideosSuccessfull() {
    LinkedList<MetadataEntity> entity = repository.findByActiveVideo();
    assertTrue(entity.size() > 0);
  }

}
