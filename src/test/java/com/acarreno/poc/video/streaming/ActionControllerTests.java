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
import com.acarreno.poc.video.streaming.controller.ActionController;
import com.acarreno.poc.video.streaming.model.ActionDTO;
import com.acarreno.poc.video.streaming.model.ActionType;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ActionController.class)
class ActionControllerTests {

  @MockBean
  private VideoStreamingService videoStreamingService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void saveAction() throws Exception {

    ActionDTO action = ActionDTO.builder().idVideo(UUID.randomUUID()).type(ActionType.COMMENTED)
        .username("guest").comment("Comment").build();

    mvc.perform(MockMvcRequestBuilders.post("/api/action").content(buildJsonString(action))
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void getStatistic() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/action/" + UUID.randomUUID().toString())
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  private String buildJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
