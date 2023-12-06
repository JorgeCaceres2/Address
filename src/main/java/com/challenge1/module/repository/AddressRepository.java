package com.challenge1.module.repository;

import com.challenge1.module.dto.AddressInputDto;
import org.openapitools.client.model.UsAutocompletions;

public interface AddressRepository {

  UsAutocompletions getAddresses(AddressInputDto addressInputDto);
}
