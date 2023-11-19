package com.joonhyuk.Subject.commerce.domain.review;


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
public class ReviewDto {

  private Long id;

  private Long productId;
  private String comment;
  private Integer stars;

  public static ReviewDto from(Review review) {
    return ReviewDto.builder()
        .id(review.getId())
        .productId(review.getProductId())
        .comment(review.getComment())
        .stars(review.getStars())
        .build();
  }
}
