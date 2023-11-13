package com.joonhyuk.Subject.commerce.domain.repository.cart;

import com.joonhyuk.Subject.commerce.domain.cart.Cart;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Cart findByCustomerIdAndProductIdAndAndProductSize(Long customerId, Long productId,String size);

  Optional<Cart> findByIdAndCustomerId(Long id, Long customerId);

  Page<Cart> findCartByCustomerId(Long customerId, Pageable pageable);

}
