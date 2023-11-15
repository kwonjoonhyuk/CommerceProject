package com.joonhyuk.Subject.commerce.domain.order.form;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelForm {

  private Long orderDetailId;

  @NotBlank(message = "환불 사유를 입력해주세요")
  private String reason;

}
