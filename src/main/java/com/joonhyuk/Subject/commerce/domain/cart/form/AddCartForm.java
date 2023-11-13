package com.joonhyuk.Subject.commerce.domain.cart.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCartForm {

  private Long productId;
  @Min(value = 1, message = "최소한 한개 이상 장바구니에 담을 수 있습니다.")
  private Integer count;

  @NotBlank(message = "사이즈를 입력해주세요")
  private String size;

}
