package com.challenge1.module.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.challenge1.module.client.impl.LobClientImpl;
import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.exception.LobClientException;
import com.challenge1.module.repository.impl.AddressRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.Suggestions;
import org.openapitools.client.model.UsAutocompletions;
import org.openapitools.client.model.UsAutocompletions.ObjectEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AddressRepositoryTest {

  @MockBean
  private LobClientImpl lobClient;

  @Autowired
  private AddressRepositoryImpl addressRepository;

  @Test
  void shouldReturnResults () {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH", null, null, null);

    //when
    when(lobClient.getAddressFromLob(inputDto)).thenReturn(getClientResponse());

    //then
    UsAutocompletions addresses = addressRepository.getAddresses(inputDto);
    assertEquals(addresses, getClientResponse());

  }

  @Test
  void shouldThrowLobClientException () {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH", null, null, null);

    //when
    when(lobClient.getAddressFromLob(inputDto)).thenThrow(new LobClientException("Error"));

    //then
    Assertions.assertThrows(LobClientException.class, () -> addressRepository.getAddresses(inputDto));

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
