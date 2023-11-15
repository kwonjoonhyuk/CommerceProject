package com.joonhyuk.Subject.commerce.domain.product;

import com.joonhyuk.Subject.commerce.aop.OrderLock;
import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.product.form.AddProductOptionForm;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class ProductOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long sellerId;
  private String size;
  private Integer count;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product product;

  public static ProductOption of(Long sellerId, AddProductOptionForm form) {
    return ProductOption.builder()
        .sellerId(sellerId)
        .size(form.getSize())
        .count(form.getCount())
        .build();
  }

  public void minusCount(Integer minusCount) {
    count = count - minusCount;
  }

  public void plusCount(Integer plusCount) {
    count = count + plusCount;
  }

}
