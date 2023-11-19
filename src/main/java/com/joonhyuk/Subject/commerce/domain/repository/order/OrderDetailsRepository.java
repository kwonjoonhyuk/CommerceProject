package com.joonhyuk.Subject.commerce.domain.repository.order;

import com.joonhyuk.Subject.commerce.domain.order.OrderDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

  Optional<OrderDetails> findByIdAndCustomerId(Long id, Long customerId);

  Page<OrderDetails> findOrderDetailsByCustomerIdOrderByModifiedAtDesc(Long customerId,
      Pageable pageable);


}
