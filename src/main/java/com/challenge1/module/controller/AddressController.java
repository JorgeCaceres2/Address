package com.challenge1.module.controller;

import com.challenge1.module.dto.AddressInputDto;
import com.challenge1.module.dto.AddressOutputDto;
import com.challenge1.module.exception.AddressException;
import com.challenge1.module.service.AddressService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/address")
public class AddressController {

  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @PostMapping
  public ResponseEntity<List<AddressOutputDto>> getAddresses(@RequestBody @Validated AddressInputDto addressInputDto) {
    log.info("Receiving request in POST /address endpoint");
    try{
      List<AddressOutputDto> response = addressService.getAddresses(addressInputDto);
      return ResponseEntity.ok(response);
    } catch (AddressException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
