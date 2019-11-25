package com.prokarma.jwt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import com.prokarma.jwt.model.AuthenticationResponse;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthenticationControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  private String validLoginPayloaf =
      "{\n" + "\"username\": \"mahesh\",\n" + "\"password\": \"mahesh\"\t\n" + "}";

  private String invalidLoginPayloaf =
      "{\n" + "\"username\": \"mahesh\",\n" + "\"password\": \"password\"\t\n" + "}";

  @Test
  void testValidAuth() throws Exception {

    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/authenticate").content(validLoginPayloaf)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String authorizationToken = result.getResponse().getContentAsString();

    AuthenticationResponse jwtResponse =
        gson.fromJson(authorizationToken, AuthenticationResponse.class);
    assertThat(jwtResponse).isNotNull().hasFieldOrProperty("jwtToken");

  }

  @Test
  void testInvalidAuth() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.post("/authenticate").content(invalidLoginPayloaf)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();
  }

  @Test
  public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/status"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }
}
