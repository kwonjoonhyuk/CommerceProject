package com.joonhyuk.Subject.commerce.controller.customer;

import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.domain.review.ReviewDto;
import com.joonhyuk.Subject.commerce.domain.review.ReviewPageResponse;
import com.joonhyuk.Subject.commerce.domain.review.form.AddReviewForm;
import com.joonhyuk.Subject.commerce.domain.review.form.UpdateReviewForm;
import com.joonhyuk.Subject.commerce.service.ReviewService;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

  private final UserCustomRepository customRepository;
  private final ReviewService reviewService;

  //리뷰 쓰기
  @PostMapping
  public ResponseEntity<ReviewDto> addReview(Authentication auth,
      @RequestBody @Valid AddReviewForm form) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(ReviewDto.from(reviewService.addReview(customerId, form)));
  }

  //리뷰 수정
  @PutMapping
  public ResponseEntity<ReviewDto> updateReview(Authentication auth,
      @RequestBody @Valid UpdateReviewForm form) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(ReviewDto.from(reviewService.updateReview(customerId, form)));
  }

  //리뷰 삭제
  @DeleteMapping
  public ResponseEntity<String> deleteReview(Authentication auth, @RequestParam Long reviewId) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    reviewService.deleteReview(customerId, reviewId);
    return ResponseEntity.ok("삭제되었습니다.");
  }

  //리뷰 확인(내가쓴 리뷰)
  @GetMapping
  public ResponseEntity<ReviewPageResponse> getReview(Authentication auth,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(reviewService.getReview(customerId, pageNo, pageSize));
  }

}
