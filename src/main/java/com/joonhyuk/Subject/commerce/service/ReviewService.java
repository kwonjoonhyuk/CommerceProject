package com.joonhyuk.Subject.commerce.service;

import static com.joonhyuk.Subject.commerce.exception.ErrorCode.DO_NOT_WRITE_REVIEW;
import static com.joonhyuk.Subject.commerce.exception.ErrorCode.NOT_FOUND_REVIEW;

import com.joonhyuk.Subject.commerce.domain.order.OrderDetails;
import com.joonhyuk.Subject.commerce.domain.order.OrderStatusEnum;
import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.repository.order.OrderDetailsRepository;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import com.joonhyuk.Subject.commerce.domain.repository.review.ReviewCustomRepository;
import com.joonhyuk.Subject.commerce.domain.repository.review.ReviewRepository;
import com.joonhyuk.Subject.commerce.domain.review.Review;
import com.joonhyuk.Subject.commerce.domain.review.ReviewDto;
import com.joonhyuk.Subject.commerce.domain.review.ReviewPageResponse;
import com.joonhyuk.Subject.commerce.domain.review.form.AddReviewForm;
import com.joonhyuk.Subject.commerce.domain.review.form.UpdateReviewForm;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final OrderDetailsRepository orderDetailsRepository;
  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewCustomRepository reviewCustomRepository;

  //리뷰 생성 상품 별점도 수정
  @Transactional
  public Review addReview(Long customerId, AddReviewForm form) {
    OrderDetails orderDetails = orderDetailsRepository.findById(form.getOrderDetailId())
        .orElseThrow(() -> new CustomException(
            ErrorCode.NOT_FOUND_ORDER_DETAILS));
    if (orderDetails.getStatus().equals(OrderStatusEnum.결제취소)) {
      throw new CustomException(DO_NOT_WRITE_REVIEW);
    }
    Product product = productRepository.findProductById(form.getProductId());
    Review review = reviewRepository.save(Review.of(customerId, form));

    Long totalCount = reviewCustomRepository.findByProductId(product.getId());
    Integer sum = reviewCustomRepository.findByStarsByProductId(product.getId());

    double stars = (double) sum / totalCount;
    product.setStars(stars);
    return review;
  }

  //리뷰 수정 별점도 같이 수정
  @Transactional
  public Review updateReview(Long customerId, UpdateReviewForm form) {
    Review review = reviewRepository.findByIdAndCustomerId(form.getId(), customerId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));

    // 리뷰 수정
    review.setStars(form.getStars());
    review.setComment(form.getComment());

    Product product = productRepository.findProductById(review.getProductId());
    // 별점도 수정
    Long totalCount = reviewCustomRepository.findByProductId(review.getProductId());
    Integer sum = reviewCustomRepository.findByStarsByProductId(review.getProductId());

    double stars = (double) sum / totalCount;
    product.setStars(stars);
    return review;
  }

  //리뷰 수정 별점도 같이 수정됨
  @Transactional
  public void deleteReview(Long customerId, Long reviewId) {
    Review review = reviewRepository.findByIdAndCustomerId(reviewId, customerId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));

    Product product = productRepository.findProductById(review.getProductId());
    reviewRepository.delete(review);

    // 별점도 수정
    Long totalCount = reviewCustomRepository.findByProductId(product.getId());
    Integer sum = reviewCustomRepository.findByStarsByProductId(product.getId());
    if (sum != null) {
      double stars = (double) sum / totalCount;
      product.setStars(stars);
    } else {
      product.setStars(0.0);
    }
  }

  //내가 쓴 리뷰 가져오기
  @Transactional(readOnly = true)
  public ReviewPageResponse getReview(Long customerId, int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<Review> reviewPage = reviewRepository.findReviewsByCustomerId(customerId, pageable);
    if (reviewPage.isEmpty()) {
      throw new CustomException(NOT_FOUND_REVIEW);
    }
    List<Review> reviews = reviewPage.getContent();
    return ReviewPageResponse.builder()
        .productList(reviews.stream().map(ReviewDto::from).collect(Collectors.toList()))
        .pageNo(pageNo + 1)
        .pageSize(pageSize)
        .totalElements(reviewPage.getTotalElements())
        .totalPages(reviewPage.getTotalPages())
        .last(reviewPage.isLast())
        .build();
  }

}
