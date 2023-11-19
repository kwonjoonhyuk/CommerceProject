package com.joonhyuk.Subject.commerce.domain.review.form;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewForm {

  private Long productId;
  private Long orderDetailId;

  @NotBlank(message = "리뷰 내용을 입력해주세요")
  private String comment;

  @Range(min = 0, max = 5, message = "0에서 5까지만 별점을 줄수 있습니다.")
  private Integer stars;


}
