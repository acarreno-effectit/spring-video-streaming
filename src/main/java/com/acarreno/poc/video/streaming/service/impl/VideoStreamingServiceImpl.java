package com.acarreno.poc.video.streaming.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.acarreno.poc.video.streaming.exception.CustomException;
import com.acarreno.poc.video.streaming.mapper.VideoStreamingMapper;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.ActionType;
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
import com.acarreno.poc.video.streaming.persistence.entity.ParticipantEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoStreamingServiceImpl implements VideoStreamingService {

  private final VideoRepository videoRepository;
  private final MetadataRepository metadataRepository;
  private final ParticipantRepository participantRepository;
  private final ActionRepository actionRepository;
  private final Uploader uploader;
  private VideoStreamingMapper mapper = VideoStreamingMapper.INSTANCE;

  @Override
  public ResponseDTO loadVideo(String fileName, byte[] content) throws CustomException {

    File file = new File(System.getProperty("java.io.tmpdir") + fileName);

    try (FileOutputStream fos = new FileOutputStream(file)) {

      fos.write(content);

      Map<?, ?> uploadResult = uploader.uploadLarge(file,
          ObjectUtils.asMap("resource_type", "video", "folder", "video_streaming"));

      VideoEntity entity = mapper.resourceToVideoEntity(fileName,
          uploadResult.get("secure_url").toString(), 123456L, StatusVideoType.LOADED.name());

      entity = videoRepository.save(entity);
      return ResponseDTO.builder().id(entity.getIdVideo().toString()).build();

    } catch (IOException e) {
      throw new CustomException(e.getMessage(), "Error loading information video");
    } finally {
      file.delete();
    }

  }

  @Override
  public VideoDTO getVideoByID(UUID idVideo) throws CustomException {

    Optional<VideoEntity> response = videoRepository.findByIdVideo(idVideo);

    if (response.isEmpty()) {
      throw new CustomException("Invalid idVideo", "ID Video is not found in the Database");
    }

    return VideoDTO.builder().filename(response.get().getFilename())
        .content(response.get().getContent()).build();
  }

  @Override
  public LinkedList<MetadataDTO> getVideosInfo() {

    LinkedList<MetadataEntity> response = metadataRepository.findByActiveVideo();
    return mapper.metadatasEntityToMetadatasDTO(response);

  }

  @Override
  public ResponseDTO changeStatusVideo(UUID idVideo, StatusVideoType statusVideo)
      throws CustomException {

    Optional<VideoEntity> response = videoRepository.findByIdVideo(idVideo);

    if (response.isEmpty()) {
      throw new CustomException("Invalid idVideo", "ID Video is not found in the Database");
    }

    VideoEntity entity = response.get();
    entity.setStatus(statusVideo.name());
    videoRepository.save(entity);

    return ResponseDTO.builder().id(entity.getIdVideo().toString()).build();
  }

  @Override
  @Transactional
  public ResponseDTO saveMetadata(MetadataDTO metadata) {

    MetadataEntity entity = mapper.metadataDTOToMetadataEntity(metadata);
    entity = metadataRepository.save(entity);

    metadata.getParticipants().forEach(item -> participantRepository
        .save(mapper.participantDTOToParticipantEntity(item, metadata.getIdVideo())));

    return ResponseDTO.builder().id(entity.getIdMetadata().toString()).build();
  }

  @Override
  @Transactional
  public MetadataDTO getMetadataByIdVideo(UUID idVideo) throws CustomException {

    Optional<MetadataEntity> response = metadataRepository.findByVideoIdVideo(idVideo);

    if (response.isEmpty()) {
      throw new CustomException("Invalid idVideo", "ID Video is not found in the Database");
    }

    MetadataDTO metadata = mapper.metadataEntityToMetadataDTO(response.get());

    LinkedList<ParticipantEntity> participantResponse =
        participantRepository.findByVideoIdVideo(idVideo);

    if (!participantResponse.isEmpty()) {
      metadata.setParticipants(mapper.participantsEntityToParticipantsDTO(participantResponse));
    }

    return metadata;
  }

  @Override
  @Transactional
  public ResponseDTO updateMetadata(MetadataDTO metadata) throws CustomException {

    Optional<MetadataEntity> response =
        metadataRepository.findByIdMetadata(metadata.getIdMetadata());

    if (response.isEmpty()) {
      throw new CustomException("Invalid idMetadata", "ID Metadata is not found in the Database");
    }

    metadata.setIdVideo(response.get().getVideo().getIdVideo());
    MetadataEntity entity = metadataRepository.save(mapper.metadataDTOToMetadataEntity(metadata));

    if (metadata.getParticipants() != null && !metadata.getParticipants().isEmpty()) {
      metadata.getParticipants().forEach(item -> participantRepository.save(participantRepository
          .save(mapper.participantDTOToParticipantEntity(item, entity.getVideo().getIdVideo()))));
    }

    return ResponseDTO.builder().id(entity.getIdMetadata().toString()).build();
  }

  @Override
  public ResponseDTO saveAction(ActionDTO action) {
    ActionEntity entity = mapper.actionDTOToActionEntity(action);
    entity = actionRepository.save(entity);
    return ResponseDTO.builder().id(entity.getIdAction().toString()).build();
  }

  @Override
  public StatisticDTO getStatisticByVideo(UUID idVideo) {

    StatisticDTO statistic = StatisticDTO.builder()
        .comments(actionRepository.countAction(ActionType.COMMENTED.name(), idVideo))
        .loads(actionRepository.countAction(ActionType.LOADED.name(), idVideo))
        .views(actionRepository.countAction(ActionType.VIEWED.name(), idVideo)).build();

    return statistic;
  }

}
