package com.acarreno.poc.video.streaming.service.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.acarreno.poc.video.streaming.mapper.VideoStreamingMapper;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.ActionType;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ResponseDTO;
import com.acarreno.poc.video.streaming.model.StatisticDTO;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.persistence.ActionRepository;
import com.acarreno.poc.video.streaming.persistence.MetadataRepository;
import com.acarreno.poc.video.streaming.persistence.ParticipantRepository;
import com.acarreno.poc.video.streaming.persistence.VideoRepository;
import com.acarreno.poc.video.streaming.persistence.entity.ActionEntity;
import com.acarreno.poc.video.streaming.persistence.entity.MetadataEntity;
import com.acarreno.poc.video.streaming.persistence.entity.ParticipantEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;
import jakarta.el.ELException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoStreamingServiceImpl implements VideoStreamingService {

  private final VideoRepository videoRepository;
  private final MetadataRepository metadataRepository;
  private final ParticipantRepository participantRepository;
  private final ActionRepository actionRepository;
  private VideoStreamingMapper mapper = VideoStreamingMapper.INSTANCE;

  @Override
  public ResponseDTO loadVideo(Resource resource) throws IOException {

    VideoEntity entity = mapper.resourceToVideoEntity(resource, resource.getContentAsByteArray(),
        resource.contentLength(), StatusVideoType.LOADED.name());
    entity = videoRepository.save(entity);

    return ResponseDTO.builder().id(entity.getIdVideo().toString()).build();
  }

  @Override
  public LinkedList<MetadataDTO> getVideosInfo() {

    LinkedList<MetadataEntity> response = metadataRepository.findByActiveVideo();
    return mapper.metadatasEntityToMetadatasDTO(response);

  }

  @Override
  public ResponseDTO changeStatusVideo(UUID idVideo, StatusVideoType statusVideo) {

    Optional<VideoEntity> response = videoRepository.findByIdVideo(idVideo);

    if (response.isEmpty()) {
      throw new ELException("Invalid idVideo");
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
  public MetadataDTO getMetadataByIdVideo(UUID idVideo) {

    Optional<MetadataEntity> response = metadataRepository.findByVideoIdVideo(idVideo);

    if (response.isEmpty()) {
      throw new ELException("Invalid idVideo");
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
  public ResponseDTO updateMetadata(MetadataDTO metadata) {

    Optional<MetadataEntity> response =
        metadataRepository.findByIdMetadata(metadata.getIdMetadata());

    if (response.isEmpty()) {
      throw new ELException("Invalid idMetadata");
    }

    metadata.setIdVideo(response.get().getVideo().getIdVideo());
    MetadataEntity entity = metadataRepository.save(mapper.metadataDTOToMetadataEntity(metadata));

    if (!metadata.getParticipants().isEmpty()) {
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
