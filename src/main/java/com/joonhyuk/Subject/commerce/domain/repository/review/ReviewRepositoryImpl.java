package com.joonhyuk.Subject.commerce.domain.repository.review;

import com.joonhyuk.Subject.commerce.domain.review.QReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {

  private final JPAQueryFactory query;

  @Override
  public Long findByProductId(Long productId) {
    QReview review = QReview.review;

    return query.select(review.count())
        .from(review)
        .where(review.productId.eq(productId)).fetchOne();
  }

  @Override
  public Integer findByStarsByProductId(Long productId) {
    QReview review = QReview.review;

    return query.select(review.stars.sum())
        .from(review)
        .where(review.productId.eq(productId)).fetchFirst();
  }
}
