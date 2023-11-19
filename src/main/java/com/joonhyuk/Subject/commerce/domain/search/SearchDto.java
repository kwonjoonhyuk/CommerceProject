package com.joonhyuk.Subject.commerce.domain.search;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class SearchDto {

  private String name;
  private String description;
  private Integer price;
  private Double stars;

  public static SearchDto from(Product product) {

    return SearchDto.builder()
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .stars(product.getStars())
        .build();
  }


}
