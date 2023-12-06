package com.challenge1.module.dto;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressInputDto {

  @NotBlank(message = "Address prefix cannot be empty")
  @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Address prefix must be alphanumeric")
  private String addressPrefix;
  @Nullable
  private String city;
  @Nullable
  private String state;
  @Nullable
  private String zipcode;

}
