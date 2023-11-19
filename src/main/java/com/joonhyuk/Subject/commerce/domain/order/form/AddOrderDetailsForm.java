package com.joonhyuk.Subject.commerce.domain.order.form;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderDetailsForm {

  private Long cartId;
  private Long optionId;

  @NotNull(message = "사이즈 입력은 필수입니다.")
  private String size;
  @NotNull(message = "수량 입력은 필수입니다.")
  private Integer count;

}
