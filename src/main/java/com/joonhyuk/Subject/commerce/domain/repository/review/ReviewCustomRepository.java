package com.joonhyuk.Subject.commerce.domain.repository.review;

public interface ReviewCustomRepository {

  Long findByProductId(Long productId);

  Integer findByStarsByProductId(Long productId);
}
