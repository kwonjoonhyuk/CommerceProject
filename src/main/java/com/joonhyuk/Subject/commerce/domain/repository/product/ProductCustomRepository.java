package com.joonhyuk.Subject.commerce.domain.repository.product;

import com.joonhyuk.Subject.commerce.domain.product.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {

  Page<Product> findProductsByCategoryOrderByModifiedAtDesc(String category, Pageable pageable);

  Page<Product> findProductsByCategoryOrderByModifiedAtAsc(String category, Pageable pageable);

  Page<Product> findProductsByCategoryOrderByPriceDesc(String category, Pageable pageable);

  Page<Product> findProductsByCategoryOrderByPriceAsc(String category, Pageable pageable);

  Page<Product> findProductsByCategoryOrderByStarsDesc(String category, Pageable pageable);

  Page<Product> findProductsByCategoryOrderByStarsAsc(String category, Pageable pageable);

  Page<Product> findProductsLikeWordOrderByModifiedAtDesc(String word, Pageable pageable);

  Page<Product> findProductsLikeWordOrderByModifiedAtAsc(String word, Pageable pageable);


}
