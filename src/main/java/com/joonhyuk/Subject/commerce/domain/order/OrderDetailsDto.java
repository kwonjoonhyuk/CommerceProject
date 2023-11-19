package com.joonhyuk.Subject.commerce.domain.order;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class OrderDetailsDto {

  private Long id;
  private String productName;
  private String description;
  private Integer totalPrice;
  private String size;
  private Integer count;
  private String orderDate;
  private OrderStatusEnum status;

  public static OrderDetailsDto from(OrderDetails orderDetails,
      ProductRepository productRepository) {

    Optional<Product> product = productRepository.findById(orderDetails.getProductId());
    String orderDate = orderDetails.getModifiedAt()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    return OrderDetailsDto.builder()
        .id(orderDetails.getId())
        .productName(product.get().getName())
        .description(product.get().getDescription())
        .totalPrice(orderDetails.getTotalPrice())
        .size(orderDetails.getSize())
        .count(orderDetails.getCount())
        .orderDate(orderDate)
        .status(orderDetails.getStatus())
        .build();
  }


}
