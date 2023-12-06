package com.challenge1.module.service;

import com.challenge1.module.client.impl.LobClientImpl;
import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.dto.AddressOutputDto;
import com.challenge1.module.exception.AddressException;
import com.challenge1.module.exception.LobClientException;
import com.challenge1.module.repository.impl.AddressRepositoryImpl;
import com.challenge1.module.service.impl.AddressServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openapitools.client.model.Suggestions;
import org.openapitools.client.model.UsAutocompletions;
import org.openapitools.client.model.UsAutocompletions.ObjectEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AddressServiceTest {

  @MockBean
  private AddressRepositoryImpl addressRepository;

  @MockBean
  private LobClientImpl lobClient;

  @Autowired
  private AddressServiceImpl addressService;

  @Test
  void shouldReturnResults() {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH", null, null, null);

    //when
    Mockito.when(addressRepository.getAddresses(inputDto)).thenReturn(getRepositoryResponse());

    //then
    List<AddressOutputDto> outputDtoList = addressService.getAddresses(inputDto);
    Assertions.assertEquals(getExpectedResult(), outputDtoList);

  }

  @Test
  void shouldThrowAddressException() {

    //given
    AddressInputDto inputDto = new AddressInputDto("1 TELEGRAPH", null, null, null);

    //when
    Mockito.when(addressRepository.getAddresses(inputDto)).thenThrow(new LobClientException("Error"));

    //then
    Assertions.assertThrows(AddressException.class, () -> addressService.getAddresses(inputDto));

  }


  private UsAutocompletions getRepositoryResponse() {
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

  private List<AddressOutputDto> getExpectedResult() {
    AddressOutputDto addressOutputDto = AddressOutputDto.builder()
        .address("1 TELEGRAPH HILL BLVD")
        .city("SAN FRANCISCO")
        .state("CA")
        .zipcode("94133")
        .build();

    return List.of(addressOutputDto);
  }
}
