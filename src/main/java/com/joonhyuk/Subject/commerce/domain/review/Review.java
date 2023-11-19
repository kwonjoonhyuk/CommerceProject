package com.joonhyuk.Subject.commerce.domain.review;

import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.review.form.AddReviewForm;
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
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long customerId;
  private Long productId;
  private String comment;
  private Integer stars;

  public static Review of(Long customerId, AddReviewForm form) {

    return Review.builder()
        .customerId(customerId)
        .productId(form.getProductId())
        .comment(form.getComment())
        .stars(form.getStars())
        .build();
  }

}
