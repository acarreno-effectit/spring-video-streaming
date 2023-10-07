package com.acarreno.poc.video.streaming.mapper;

import java.util.LinkedList;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.Resource;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ParticipantDTO;
import com.acarreno.poc.video.streaming.persistence.entity.ActionEntity;
import com.acarreno.poc.video.streaming.persistence.entity.MetadataEntity;
import com.acarreno.poc.video.streaming.persistence.entity.ParticipantEntity;
import com.acarreno.poc.video.streaming.persistence.entity.VideoEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VideoStreamingMapper {

  VideoStreamingMapper INSTANCE = Mappers.getMapper(VideoStreamingMapper.class);

  @Mapping(target = "status", source = "status")
  @Mapping(target = "filename", source = "resource.filename")
  @Mapping(target = "content", source = "contentAsByteArray")
  @Mapping(target = "sizeInBytes", source = "contentLength")
  VideoEntity resourceToVideoEntity(Resource resource, byte[] contentAsByteArray,
      long contentLength, String status);

  @Mapping(target = "video.idVideo", source = "dto.idVideo")
  MetadataEntity metadataDTOToMetadataEntity(MetadataDTO dto);

  @Mapping(target = "video.idVideo", source = "idVideo")
  ParticipantEntity participantDTOToParticipantEntity(ParticipantDTO dto, UUID idVideo);

  @Mapping(target = "video.idVideo", source = "idVideo")
  ActionEntity actionDTOToActionEntity(ActionDTO dto);

  @Mapping(target = "idVideo", source = "video.idVideo")
  MetadataDTO metadataEntityToMetadataDTO(MetadataEntity entity);

  LinkedList<ParticipantDTO> participantsEntityToParticipantsDTO(
      LinkedList<ParticipantEntity> entity);

  LinkedList<MetadataDTO> metadatasEntityToMetadatasDTO(LinkedList<MetadataEntity> entity);

}
