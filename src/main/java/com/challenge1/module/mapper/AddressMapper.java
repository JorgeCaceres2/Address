package com.challenge1.module.mapper;

import com.challenge1.module.dto.AddressOutputDto;
import java.util.List;
import java.util.stream.Collectors;
import org.openapitools.client.model.UsAutocompletions;

public class AddressMapper {

  public List<AddressOutputDto> fromUsAutocompletions(UsAutocompletions usAutocompletions) {
    return usAutocompletions.getSuggestions().stream()
        .map(suggestion -> AddressOutputDto.builder()
            .address(suggestion.getPrimaryLine())
            .city(suggestion.getCity())
            .state(suggestion.getState())
            .zipcode(suggestion.getZipCode())
            .build())
        .collect(Collectors.toList());
  }
}
