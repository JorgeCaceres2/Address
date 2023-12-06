package com.challenge1.module.client;

import com.challenge1.module.dto.AddressInputDto;
import org.openapitools.client.model.UsAutocompletions;

public interface LobClient {

  UsAutocompletions getAddressFromLob(AddressInputDto addressInputDto);

}
