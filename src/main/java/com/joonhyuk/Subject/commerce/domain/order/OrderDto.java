package com.joonhyuk.Subject.commerce.domain.order;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
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
public class OrderDto {


  private Long id;

  private String productName;
  private Integer totalPrice;
  private String size;
  private Integer count;
  private OrderStatusEnum status;

  public static OrderDto from(OrderDetails orderDetails, ProductRepository productRepository) {
    Product product = productRepository.findProductById(orderDetails.getProductId());
    return OrderDto.builder()
        .id(orderDetails.getId())
        .productName(product.getName())
        .totalPrice(orderDetails.getTotalPrice())
        .size(orderDetails.getSize())
        .count(orderDetails.getCount())
        .status(orderDetails.getStatus())
        .build();
  }
}
