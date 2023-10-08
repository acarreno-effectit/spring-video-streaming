package com.acarreno.poc.video.streaming.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;
import org.springframework.core.io.Resource;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ResponseDTO;
import com.acarreno.poc.video.streaming.model.StatisticDTO;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.model.VideoDTO;

public interface VideoStreamingService {

  ResponseDTO loadVideo(Resource resource) throws IOException;

  VideoDTO getVideoByID(UUID idVideo);

  LinkedList<MetadataDTO> getVideosInfo();

  ResponseDTO changeStatusVideo(UUID idVideo, StatusVideoType statusVideo);

  ResponseDTO saveMetadata(MetadataDTO metadata);

  MetadataDTO getMetadataByIdVideo(UUID idVideo);

  ResponseDTO updateMetadata(MetadataDTO metadata);

  ResponseDTO saveAction(ActionDTO action);

  StatisticDTO getStatisticByVideo(UUID idVideo);

}
