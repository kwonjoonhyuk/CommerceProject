package com.joonhyuk.Subject.commerce.domain.repository.review;

import com.joonhyuk.Subject.commerce.domain.review.Review;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  Page<Review> findReviewsByCustomerId(Long customerId, Pageable pageable);

  Optional<Review> findByIdAndCustomerId(Long id, Long customerId);

}
