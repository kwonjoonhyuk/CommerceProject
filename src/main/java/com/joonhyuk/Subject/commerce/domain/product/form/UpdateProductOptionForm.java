package com.joonhyuk.Subject.commerce.domain.product.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductOptionForm {

  private Long id;
  private Long productId;
  private String size;
  private Integer count;
}
