package com.joonhyuk.Subject.commerce.domain.review;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPageResponse {

  private List<ReviewDto> productList;
  private int pageNo;
  private int pageSize;
  private Long totalElements;
  private int totalPages;
  private boolean last;
}
