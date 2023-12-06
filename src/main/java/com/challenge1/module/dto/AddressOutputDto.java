package com.challenge1.module.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AddressOutputDto {

  private String address;
  private String city;
  private String state;
  private String zipcode;

}
