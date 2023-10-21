package com.acarreno.poc.video.streaming.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.acarreno.poc.video.streaming.exception.CustomException;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.ActionType;
import com.acarreno.poc.video.streaming.model.GenreType;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ResponseDTO;
import com.acarreno.poc.video.streaming.model.StatisticDTO;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.model.VideoDTO;
import com.acarreno.poc.video.streaming.persistence.ActionRepository;
import com.acarreno.poc.video.streaming.persistence.MetadataRepository;
import com.acarreno.poc.video.streaming.persistence.ParticipantRepository;
import com.acarreno.poc.video.streaming.persistence.VideoRepository;
import com.acarreno.poc.video.streaming.persistence.entity.ActionEntity;
import com.acarreno.poc.video.streaming.persistence.entity.MetadataEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;
import com.acarreno.poc.video.streaming.service.impl.VideoStreamingServiceImpl;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;

@SpringBootTest
public class VideoStreamingServiceTest {

  @Mock
  private VideoRepository videoRepository;

  @Mock
  private MetadataRepository metadataRepository;

  @Mock
  private ParticipantRepository participantRepository;

  @Mock
  private ActionRepository actionRepository;

  @Mock
  private Uploader uploader;

  @InjectMocks
  private VideoStreamingServiceImpl service;

  @Test
  public void loadVideoSuccessful() throws CustomException, IOException {

    VideoEntity entity = VideoEntity.builder().idVideo(UUID.randomUUID()).build();
    Map<?, ?> returnMap = ObjectUtils.asMap("secure_url", "https://url-video");
    File file = new File(System.getProperty("java.io.tmpdir") + "fileName.txt");

    when(uploader.uploadLarge(file,
        ObjectUtils.asMap("resource_type", "video", "folder", "video_streaming")))
            .thenReturn(returnMap);

    when(videoRepository.save(Mockito.any(VideoEntity.class))).thenReturn(entity);
    ResponseDTO response = service.loadVideo("fileName.txt", "myVideoContent".getBytes());
    assertTrue(response.getId() != null);
  }

  @Test
  public void getVideoByIDSuccessful() throws CustomException {
    VideoEntity entity = VideoEntity.builder().filename("filename.mp4").content("MyVideo").build();

    when(videoRepository.findByIdVideo(Mockito.any(UUID.class))).thenReturn(Optional.of(entity));
    VideoDTO response = service.getVideoByID(mock(UUID.class));
    assertTrue(response.getFilename() != null && response.getContent() != null);
  }

  @Test
  public void getVideoByIDELException() {
    CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
      when(videoRepository.findByIdVideo(Mockito.any(UUID.class))).thenReturn(Optional.empty());
      service.getVideoByID(mock(UUID.class));
    });

    assertEquals("Invalid idVideo", thrown.getMessage());
  }

  @Test
  public void getVideosInfoSuccessful() {
    MetadataEntity entity =
        MetadataEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .genre(GenreType.ACTION.name()).runningTime("2h 30m").synopsis("Synopsis test")
            .createdDate(LocalDateTime.now()).title("My Title").yearOfRelease(2023).build();

    LinkedList<MetadataEntity> list = new LinkedList<>();
    list.add(entity);

    when(metadataRepository.findByActiveVideo()).thenReturn(list);
    LinkedList<MetadataDTO> response = service.getVideosInfo();
    assertTrue(response.size() > 0);
  }

  @Test
  public void getVideosInfoEmpty() {
    when(metadataRepository.findByActiveVideo()).thenReturn(new LinkedList<>());
    LinkedList<MetadataDTO> response = service.getVideosInfo();
    assertTrue(response.isEmpty());
  }

  @Test
  public void changeStatusVideoSuccessful() throws CustomException {
    VideoEntity entity = VideoEntity.builder().idVideo(UUID.randomUUID()).filename("filename.mp4")
        .content("MyVideo").build();

    when(videoRepository.findByIdVideo(Mockito.any(UUID.class))).thenReturn(Optional.of(entity));
    ResponseDTO response = service.changeStatusVideo(mock(UUID.class), StatusVideoType.ACTIVED);
    assertTrue(response.getId() != null);
  }

  @Test
  public void changeStatusVideoNotFoundVideo() {
    CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
      when(videoRepository.findByIdVideo(Mockito.any(UUID.class))).thenReturn(Optional.empty());
      service.changeStatusVideo(mock(UUID.class), StatusVideoType.ACTIVED);
    });

    assertEquals("Invalid idVideo", thrown.getMessage());
  }

  @Test
  public void saveMetadataSuccessful() {

    MetadataEntity entity =
        MetadataEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .idMetadata(UUID.randomUUID()).genre(GenreType.ACTION.name()).runningTime("2h 30m")
            .synopsis("Synopsis test").createdDate(LocalDateTime.now()).title("My Title")
            .yearOfRelease(2023).build();

    when(metadataRepository.save(Mockito.any(MetadataEntity.class))).thenReturn(entity);

    ResponseDTO response = service.saveMetadata(mock(MetadataDTO.class));
    assertTrue(response.getId() != null);
  }

  @Test
  public void getMetadataByIdVideoSuccessful() throws CustomException {

    MetadataEntity entity =
        MetadataEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .idMetadata(UUID.randomUUID()).genre(GenreType.ACTION.name()).runningTime("2h 30m")
            .synopsis("Synopsis test").createdDate(LocalDateTime.now()).title("My Title")
            .yearOfRelease(2023).build();

    when(metadataRepository.findByVideoIdVideo(Mockito.any(UUID.class)))
        .thenReturn(Optional.of(entity));
    MetadataDTO response = service.getMetadataByIdVideo(mock(UUID.class));
    assertTrue(response != null);
  }

  @Test
  public void getMetadataByIdVideoNotFound() {

    CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
      when(metadataRepository.findByVideoIdVideo(Mockito.any(UUID.class)))
          .thenReturn(Optional.empty());
      service.getMetadataByIdVideo(mock(UUID.class));
    });

    assertEquals("Invalid idVideo", thrown.getMessage());
  }

  @Test
  public void updateMetadataSuccessful() throws CustomException {
    MetadataEntity entity =
        MetadataEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .idMetadata(UUID.randomUUID()).genre(GenreType.ACTION.name()).runningTime("2h 30m")
            .synopsis("Synopsis test").createdDate(LocalDateTime.now()).title("My Title")
            .yearOfRelease(2023).build();

    when(metadataRepository.findByIdMetadata(Mockito.any(UUID.class)))
        .thenReturn(Optional.of(entity));
    when(metadataRepository.save(Mockito.any(MetadataEntity.class))).thenReturn(entity);
    ResponseDTO response =
        service.updateMetadata(MetadataDTO.builder().idMetadata(UUID.randomUUID()).build());
    assertTrue(response.getId() != null);
  }

  @Test
  public void updateMetadataNotFound() {

    CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
      when(metadataRepository.findByIdMetadata(Mockito.any(UUID.class)))
          .thenReturn(Optional.empty());
      service.updateMetadata(mock(MetadataDTO.class));
    });

    assertEquals("Invalid idMetadata", thrown.getMessage());
  }

  @Test
  public void saveActionSuccessful() {
    ActionEntity entity =
        ActionEntity.builder().video(VideoEntity.builder().idVideo(UUID.randomUUID()).build())
            .idAction(UUID.randomUUID()).username("guest").type(ActionType.LOADED.name())
            .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

    when(actionRepository.save(Mockito.any(ActionEntity.class))).thenReturn(entity);
    ResponseDTO response = service.saveAction(mock(ActionDTO.class));
    assertTrue(response.getId() != null);
  }

  @Test
  public void getStatisticByVideoSuccessful() {
    when(actionRepository.countAction(Mockito.any(String.class), Mockito.any(UUID.class))).thenReturn(1);
    StatisticDTO response = service.getStatisticByVideo(mock(UUID.class));
    assertTrue(response.getComments()> 0 && response.getViews() > 0 && response.getLoads() > 0);
  }
}
