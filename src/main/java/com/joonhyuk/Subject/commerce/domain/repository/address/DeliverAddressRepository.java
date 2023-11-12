package com.joonhyuk.Subject.commerce.domain.repository.address;

import com.joonhyuk.Subject.commerce.domain.Customer.DeliveryAddress;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverAddressRepository extends JpaRepository<DeliveryAddress, Long> {

  Optional<DeliveryAddress> findByCustomerIdAndId(Long customerId, Long addressId);

  Page<DeliveryAddress> findDeliveryAddressByCustomerId(Long customerId, Pageable pageable);
}
