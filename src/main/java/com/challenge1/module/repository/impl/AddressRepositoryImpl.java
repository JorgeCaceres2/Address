package com.challenge1.module.repository.impl;

import com.challenge1.module.client.LobClient;
import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.client.model.UsAutocompletions;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AddressRepositoryImpl implements AddressRepository {

  private final LobClient lobClient;

  public AddressRepositoryImpl(LobClient lobClient) {
    this.lobClient = lobClient;
  }

  @Override
  public UsAutocompletions getAddresses(AddressInputDto addressInputDto) {
    log.info("Requesting addresses to repository");
    return lobClient.getAddressFromLob(addressInputDto);
  }
}
