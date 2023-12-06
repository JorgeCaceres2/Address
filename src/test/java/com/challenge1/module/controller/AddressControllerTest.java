package com.challenge1.module.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.dto.AddressOutputDto;
import com.challenge1.module.exception.AddressException;
import com.challenge1.module.service.impl.AddressServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class AddressControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AddressServiceImpl addressService;

  @Value("${spring.security.user.name}")
  private String username;

  @Value("${spring.security.user.password}")
  private String password;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReturnAddresses() throws Exception {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH",null, null, null);
    String jsonRequest = objectMapper.writeValueAsString(inputDto);
    List <AddressOutputDto> expectedResult = getExpectedResult();

    //when
    when(addressService.getAddresses(inputDto)).thenReturn(expectedResult);

    //then
    mockMvc.perform(post("/address")
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Basic " + getBase64Credentials()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void shouldReturnBadRequestErrorDueToEmptyAddressPrefix() throws Exception {

    //given
    AddressInputDto inputDto = new AddressInputDto(null,null, null, null);
    String jsonRequest = objectMapper.writeValueAsString(inputDto);

    //then
    mockMvc.perform(post("/address")
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON) // Set Content-Type header
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Basic " + getBase64Credentials()))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnAddressException() throws Exception {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH",null, null, null);
    String jsonRequest = objectMapper.writeValueAsString(inputDto);

    //when
    when(addressService.getAddresses(inputDto)).thenThrow(new AddressException("Error"));

    //then
    mockMvc.perform(post("/address")
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON) // Set Content-Type header
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Basic " + getBase64Credentials()))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  private String getBase64Credentials() {
    String credentials = username + ":" + password;
    return Base64.getEncoder().encodeToString(credentials.getBytes());
  }

  private List<AddressOutputDto> getExpectedResult() {
    AddressOutputDto addressOutputDto = AddressOutputDto.builder()
        .address("1 TELEGRAPH HILL")
        .city("SAN FRANCISCO")
        .state("CA")
        .zipcode("94133")
        .build();

    return List.of(addressOutputDto);
  }
}
