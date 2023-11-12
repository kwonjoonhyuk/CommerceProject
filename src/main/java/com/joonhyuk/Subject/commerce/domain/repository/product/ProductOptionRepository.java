package com.joonhyuk.Subject.commerce.domain.repository.product;

import com.joonhyuk.Subject.commerce.domain.product.ProductOption;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

  Optional<ProductOption> findBySellerIdAndId(Long sellerId, Long optionId);

  Page<ProductOption> findProductOptionsBySellerId(Long sellerId, Pageable pageable);

}
