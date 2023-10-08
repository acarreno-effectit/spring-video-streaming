package com.acarreno.poc.video.streaming.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.acarreno.poc.video.streaming.model.ActionType;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.persistence.entity.ActionEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActionRepositoryTest {

  private UUID idVideo;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ActionRepository repository;

  @BeforeEach
  void setUp() {

    VideoEntity entity = VideoEntity.builder().filename("filename.mp4").content("video".getBytes())
        .status(StatusVideoType.LOADED.name()).build();

    entity = entityManager.persist(entity);

    ActionEntity entity1 =
        ActionEntity.builder().video(VideoEntity.builder().idVideo(entity.getIdVideo()).build())
            .username("guest").type(ActionType.LOADED.name()).createdDate(LocalDateTime.now())
            .updatedDate(LocalDateTime.now()).build();

    entityManager.persist(entity1);

    ActionEntity entity2 =
        ActionEntity.builder().video(VideoEntity.builder().idVideo(entity.getIdVideo()).build())
            .username("guest").type(ActionType.COMMENTED.name()).createdDate(LocalDateTime.now())
            .updatedDate(LocalDateTime.now()).build();

    entityManager.persist(entity2);

    ActionEntity entity3 =
        ActionEntity.builder().video(VideoEntity.builder().idVideo(entity.getIdVideo()).build())
            .username("guest").type(ActionType.VIEWED.name()).createdDate(LocalDateTime.now())
            .updatedDate(LocalDateTime.now()).build();

    entityManager.persist(entity3);
    this.idVideo = entity.getIdVideo();
  }

  @Test
  public void saveActionSuccessful() {
    ActionEntity entity =
        ActionEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .username("guest").type(ActionType.LOADED.name()).build();

    entity = repository.save(entity);
    assertThat(entity).hasFieldOrProperty("idAction");
  }

  @Test
  public void countsActionByIdVideo() {
    Integer comments = repository.countAction(ActionType.COMMENTED.name(), idVideo);
    Integer loads = repository.countAction(ActionType.LOADED.name(), idVideo);
    Integer views = repository.countAction(ActionType.VIEWED.name(), idVideo);
    assertTrue(comments > 0 && loads > 0 && views > 0);
  }

}
