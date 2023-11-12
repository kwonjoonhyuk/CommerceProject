package com.joonhyuk.Subject.commerce.domain.product;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

  @NotNull
  private Long id;

  private String category;
  private String name;
  private Integer price;
  private String description;
  private Double stars;
  private List<ProductOptionDto> productOptionList;

  public static ProductDto from(Product product) {
    List<ProductOptionDto> productOptionDtos = product.getProductOptions()
        .stream().map(ProductOptionDto::from).collect(Collectors.toList());

    return ProductDto.builder()
        .id(product.getId())
        .category(product.getCategory())
        .name(product.getName())
        .price(product.getPrice())
        .description(product.getDescription())
        .stars(product.getStars())
        .productOptionList(productOptionDtos)
        .build();
  }


  public static ProductDto exceptOption(Product product) {

    return ProductDto.builder()
        .id(product.getId())
        .category(product.getCategory())
        .name(product.getName())
        .price(product.getPrice())
        .description(product.getDescription())
        .stars(product.getStars())
        .build();
  }

}
