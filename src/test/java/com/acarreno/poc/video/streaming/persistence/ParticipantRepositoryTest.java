package com.acarreno.poc.video.streaming.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.acarreno.poc.video.streaming.model.ParticipantType;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.persistence.entity.ParticipantEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParticipantRepositoryTest {

  private UUID idVideo;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ParticipantRepository repository;

  @BeforeEach
  void setUp() {

    VideoEntity entity = VideoEntity.builder().filename("filename.mp4").content("video".getBytes())
        .status(StatusVideoType.LOADED.name()).build();

    entity = entityManager.persist(entity);

    ParticipantEntity entity1 = ParticipantEntity.builder()
        .video(VideoEntity.builder().idVideo(entity.getIdVideo()).build()).firstname("Brad")
        .lastname("Pitt").type(ParticipantType.MAIN.name()).createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now()).build();

    entityManager.persist(entity1);

    ParticipantEntity entity2 = ParticipantEntity.builder()
        .video(VideoEntity.builder().idVideo(entity.getIdVideo()).build()).firstname("Leonardo")
        .lastname("DiCaprio").type(ParticipantType.SECONDARY.name())
        .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

    entityManager.persist(entity2);

    ParticipantEntity entity3 = ParticipantEntity.builder()
        .video(VideoEntity.builder().idVideo(entity.getIdVideo()).build()).firstname("Joseph")
        .lastname("Gordon-Levitt").type(ParticipantType.SECONDARY.name())
        .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

    entityManager.persist(entity3);

    this.idVideo = entity.getIdVideo();
  }

  @Test
  public void saveParticipantSuccessful() {
    ParticipantEntity entity =
        ParticipantEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .firstname("Brad").lastname("Pitt").type(ParticipantType.MAIN.name())
            .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

    entity = repository.save(entity);
    assertThat(entity).hasFieldOrProperty("idParticipant");
  }

  @Test
  public void findParticipantsByIdVideoSuccessful() {
    LinkedList<ParticipantEntity> entity = repository.findByVideoIdVideo(idVideo);
    assertTrue(entity.size() > 0);
  }

}
