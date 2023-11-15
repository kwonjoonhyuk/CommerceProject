package com.joonhyuk.Subject.commerce.domain.cart.form;

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
public class UpdateCartForm {

  @NotNull
  private Long id;

  @NotBlank(message = "사이즈를 입력해주세요")
  private String size;
  private Integer count;


}
