package com.joonhyuk.Subject.commerce.domain.cart;

import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.cart.form.AddCartForm;
import javax.persistence.Entity;
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
public class Cart extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long customerId;
  private Long productId;
  private Integer productCount;
  private String productSize;


  public static Cart of(Long customerId, AddCartForm form) {
    return Cart.builder()
        .customerId(customerId)
        .productId(form.getProductId())
        .productCount(form.getCount())
        .productSize(form.getSize())
        .build();
  }

}
