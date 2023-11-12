package com.joonhyuk.Subject.commerce.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDto {

  private Long id;
  private String size;
  private Integer count;

  public static ProductOptionDto from(ProductOption option) {
    return ProductOptionDto.builder()
        .id(option.getId())
        .size(option.getSize())
        .count(option.getCount())
        .build();
  }

}
