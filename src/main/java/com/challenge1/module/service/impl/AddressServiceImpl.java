package com.challenge1.module.service.impl;

import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.dto.AddressOutputDto;
import com.challenge1.module.exception.AddressException;
import com.challenge1.module.exception.LobClientException;
import com.challenge1.module.mapper.AddressMapper;
import com.challenge1.module.repository.AddressRepository;
import com.challenge1.module.service.AddressService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.client.model.UsAutocompletions;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final AddressMapper addressMapper = new AddressMapper();

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public List<AddressOutputDto> getAddresses(AddressInputDto addressInputDto) {
    log.info("Requesting addresses to Address Service");
    UsAutocompletions autocompletion;

    try {
      autocompletion = addressRepository.getAddresses(addressInputDto);
    } catch (LobClientException e) {
      throw new AddressException("An error occurs in address service. Please contact customer support");
    }

    List<AddressOutputDto> addressOutputDtoList = addressMapper.fromUsAutocompletions(autocompletion);
    log.info("Returning mapped addresses result: {}", addressOutputDtoList);
    return addressOutputDtoList;

  }

}
