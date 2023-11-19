package com.joonhyuk.Subject.commerce.domain.order;

import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.order.form.AddOrderDetailsForm;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class OrderDetails extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long customerId;
  private Long productId;
  private Long cartId;
  private Long optionId;
  private Integer totalPrice;
  private Integer count;
  private String size;

  @Enumerated(EnumType.STRING)
  private OrderStatusEnum status;
  private String reason;

  public static OrderDetails of(Long customerId, AddOrderDetailsForm form, Integer totalPrice,
      Long productId) {

    return OrderDetails.builder()
        .customerId(customerId)
        .cartId(form.getCartId())
        .optionId(form.getOptionId())
        .productId(productId)
        .size(form.getSize())
        .count(form.getCount())
        .totalPrice(totalPrice)
        .status(OrderStatusEnum.결제완료)
        .reason(null)
        .build();
  }

}
