package com.joonhyuk.Subject.commerce.domain.product.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductOptionForm {

  private Long productId;
  private String size;
  private Integer count;

}
