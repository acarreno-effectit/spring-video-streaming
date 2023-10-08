package com.acarreno.poc.video.streaming;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.acarreno.poc.video.streaming.controller.VideoController;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;

@WebMvcTest(VideoController.class)
public class VideoControllerTest {

  @MockBean
  private VideoStreamingService videoStreamingService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void statusVideo() throws Exception {
    mvc.perform(MockMvcRequestBuilders.patch("/api/video")
        .param("idVideo", UUID.randomUUID().toString()).param("status", "ACTIVED")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getVideos() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/video").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getVideoById() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/video/" + UUID.randomUUID().toString())
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

}
