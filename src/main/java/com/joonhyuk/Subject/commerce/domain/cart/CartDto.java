package com.joonhyuk.Subject.commerce.domain.cart;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class CartDto {


  private Long id;
  private Long productId;
  private String name;
  private String category;
  private Integer price;
  private Double stars;
  private String size;
  private Integer count;


  public static CartDto from(Cart cart, ProductRepository productRepository) {

    Optional<Product> product = productRepository.findById(cart.getProductId());
    return CartDto.builder()
        .id(cart.getId())
        .productId(cart.getProductId())
        .name(product.get().getName())
        .category(product.get().getCategory())
        .price(product.get().getPrice())
        .stars(product.get().getStars())
        .size(cart.getProductSize())
        .count(cart.getProductCount())
        .build();

  }



}
