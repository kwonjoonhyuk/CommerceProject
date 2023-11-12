package com.joonhyuk.Subject.commerce.domain.Customer.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDeliverAddressForm {

  @NotNull
  private Long id;
  @NotNull
  private String address;

  @NotBlank(message = "상세주소가 입력되지 않았습니다.")
  private String address_detail;




}
