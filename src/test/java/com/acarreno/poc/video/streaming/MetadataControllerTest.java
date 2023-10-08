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
import com.acarreno.poc.video.streaming.controller.MetadataController;
import com.acarreno.poc.video.streaming.model.GenreType;
import com.acarreno.poc.video.streaming.model.MetadataDTO;
import com.acarreno.poc.video.streaming.service.VideoStreamingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MetadataController.class)
public class MetadataControllerTest {

  @MockBean
  private VideoStreamingService videoStreamingService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void saveMetadata() throws Exception {
    MetadataDTO metadata =
        MetadataDTO.builder().genre(GenreType.ACTION.name()).runningTime("2h 30m")
            .synopsis("Synopsis test").title("My Title").yearOfRelease(2023).build();

    mvc.perform(MockMvcRequestBuilders.post("/api/metadata").content(buildJsonString(metadata))
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void upateMetadata() throws Exception {
    MetadataDTO metadata =
        MetadataDTO.builder().genre(GenreType.ACTION.name()).runningTime("2h 30m")
            .synopsis("Synopsis test").title("My Title").yearOfRelease(2023).build();

    mvc.perform(MockMvcRequestBuilders.put("/api/metadata").content(buildJsonString(metadata))
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getMetadata() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/metadata/" + UUID.randomUUID().toString())
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
