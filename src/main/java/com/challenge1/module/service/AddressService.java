package com.challenge1.module.service;

import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.dto.AddressOutputDto;
import java.util.List;

public interface AddressService {

  List<AddressOutputDto> getAddresses(AddressInputDto addressInputDto);

}
