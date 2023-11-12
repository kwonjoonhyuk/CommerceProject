package com.joonhyuk.Subject.commerce.domain.Customer.form;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAddressForm {

  @NotBlank
  private String address;
  private String address_detail;
}
