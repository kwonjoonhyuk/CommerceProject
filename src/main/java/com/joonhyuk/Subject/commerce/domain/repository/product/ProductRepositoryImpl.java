package com.joonhyuk.Subject.commerce.domain.repository.product;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.product.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;
  QProduct product = QProduct.product;

  // 카테고리별 최신순 검색
  @Override
  public Page<Product> findProductsByCategoryOrderByModifiedAtDesc(String category,
      Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .orderBy(product.modifiedAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }

  // 카테고리별 오래된순 검색
  @Override
  public Page<Product> findProductsByCategoryOrderByModifiedAtAsc(String category,
      Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .orderBy(product.modifiedAt.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }

  // 카테고리 가격 낮은순으로 검색
  @Override
  public Page<Product> findProductsByCategoryOrderByPriceAsc(String category, Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .orderBy(product.price.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }

  // 카테고리별 가격 높은순으로 검색
  @Override
  public Page<Product> findProductsByCategoryOrderByPriceDesc(String category, Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .orderBy(product.price.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }
  // 카테고리별 별점 높은순으로 검색
  @Override
  public Page<Product> findProductsByCategoryOrderByStarsDesc(String category, Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .orderBy(product.stars.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }

  // 카테고리별 별점 낮은순으로 검색
  @Override
  public Page<Product> findProductsByCategoryOrderByStarsAsc(String category, Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .orderBy(product.stars.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.category.eq(category))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }

  // 검색어로 검색 최신순
  @Override
  public Page<Product> findProductsLikeWordOrderByModifiedAtDesc(String word, Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.name.contains(word))
        .orderBy(product.modifiedAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.name.like(word))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }

  // 검색어로 검색 오래된순
  @Override
  public Page<Product> findProductsLikeWordOrderByModifiedAtAsc(String word, Pageable pageable) {
    List<Product> results = jpaQueryFactory.selectFrom(product)
        .where(product.name.contains(word))
        .orderBy(product.modifiedAt.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<Product> productList = jpaQueryFactory.selectFrom(product)
        .where(product.name.like(word))
        .fetch();

    int totalCount = productList.size();
    return new PageImpl<>(results, pageable, totalCount);
  }


}
