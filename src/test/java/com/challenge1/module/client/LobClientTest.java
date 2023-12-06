package com.challenge1.module.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.challenge1.module.client.impl.LobClientImpl;
import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.exception.LobClientException;
import com.lob.api.ApiClient;
import com.lob.api.ApiException;
import com.lob.api.auth.HttpBasicAuth;
import com.lob.api.client.UsAutocompletionsApi;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openapitools.client.model.Suggestions;
import org.openapitools.client.model.UsAutocompletions;
import org.openapitools.client.model.UsAutocompletions.ObjectEnum;
import org.openapitools.client.model.UsAutocompletionsWritable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LobClientTest {


  private LobClientImpl lobClient;

  @MockBean
  private ApiClient lobApiClient;

  @MockBean
  private UsAutocompletionsApi usAutocompletionsApi;

  @BeforeEach
  void setup() {
    lobClient = new LobClientImpl(lobApiClient, usAutocompletionsApi);
  }

  @Test
  void shouldReturnResults() throws ApiException {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH", null, null, null);
    HttpBasicAuth basicAuthMock = Mockito.mock(HttpBasicAuth.class);

    // when
    when(lobApiClient.getAuthentication("basicAuth")).thenReturn(basicAuthMock);
    when(usAutocompletionsApi.autocomplete(getAutoCompletionWritableRequest(inputDto))).thenReturn(getClientResponse());

    // then
    UsAutocompletions result = lobClient.getAddressFromLob(inputDto);
    Assertions.assertEquals(getClientResponse(), result);

  }

  @Test
  void shouldThrowLobClientException() throws ApiException {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH", null, null, null);
    HttpBasicAuth basicAuthMock = Mockito.mock(HttpBasicAuth.class);

    // when
    when(lobApiClient.getAuthentication("basicAuth")).thenReturn(basicAuthMock);
    when(usAutocompletionsApi.autocomplete(getAutoCompletionWritableRequest(inputDto))).thenThrow(new ApiException("Error"));

    // then
    assertThrows(LobClientException.class, () -> lobClient.getAddressFromLob(inputDto));

  }

  private UsAutocompletionsWritable getAutoCompletionWritableRequest(AddressInputDto addressInputDto) {
    UsAutocompletionsWritable autoCompletionWritable = new UsAutocompletionsWritable();
    autoCompletionWritable.setAddressPrefix(addressInputDto.getAddressPrefix());
    autoCompletionWritable.setCity(addressInputDto.getCity());
    autoCompletionWritable.setState(addressInputDto.getState());
    autoCompletionWritable.setZipCode(addressInputDto.getZipcode());

    return autoCompletionWritable;
  }

  private UsAutocompletions getClientResponse() {
    Suggestions suggestions = new Suggestions();
    suggestions.setPrimaryLine("1 TELEGRAPH HILL BLVD");
    suggestions.setCity("SAN FRANCISCO");
    suggestions.setZipCode("94133");
    suggestions.setState("CA");
    suggestions.setObject(Suggestions.ObjectEnum.US_AUTOCOMPLETION);

    UsAutocompletions usAutocompletions = new UsAutocompletions();
    usAutocompletions.setId("us_auto_2fe76768e02b4e789c9a");
    usAutocompletions.setObject(ObjectEnum.US_AUTOCOMPLETION);
    usAutocompletions.setSuggestions(List.of(suggestions));

    return usAutocompletions;
  }

}
