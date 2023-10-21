package com.acarreno.poc.video.streaming.service;

import java.util.LinkedList;
import java.util.UUID;
import com.acarreno.poc.video.streaming.exception.CustomException;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.model.ResponseDTO;
import com.acarreno.poc.video.streaming.model.StatisticDTO;
import com.acarreno.poc.video.streaming.model.StatusVideoType;
import com.acarreno.poc.video.streaming.model.VideoDTO;

public interface VideoStreamingService {

  ResponseDTO loadVideo(String fileName, byte[] content) throws CustomException;

  VideoDTO getVideoByID(UUID idVideo) throws CustomException;

  LinkedList<MetadataDTO> getVideosInfo();

  ResponseDTO changeStatusVideo(UUID idVideo, StatusVideoType statusVideo) throws CustomException;

  ResponseDTO saveMetadata(MetadataDTO metadata);

  MetadataDTO getMetadataByIdVideo(UUID idVideo) throws CustomException;

  ResponseDTO updateMetadata(MetadataDTO metadata) throws CustomException;

  ResponseDTO saveAction(ActionDTO action);

  StatisticDTO getStatisticByVideo(UUID idVideo);

}
