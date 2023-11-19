package com.joonhyuk.Subject.commerce.domain.repository.product;

import com.joonhyuk.Subject.commerce.domain.product.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findBySellerIdAndId(Long sellerId, Long productId);

  Product findProductById(Long Id);

  Page<Product> findProductBySellerId(Long sellerId, Pageable pageable);


}
