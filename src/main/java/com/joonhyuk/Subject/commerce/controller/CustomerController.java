package com.joonhyuk.Subject.commerce.controller;

import com.joonhyuk.Subject.commerce.domain.Customer.AddressPageResponse;
import com.joonhyuk.Subject.commerce.domain.Customer.DeliveryAddressDto;
import com.joonhyuk.Subject.commerce.domain.Customer.form.AddAddressForm;
import com.joonhyuk.Subject.commerce.domain.Customer.form.UpdateDeliverAddressForm;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.service.CustomerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

  private final UserCustomRepository customRepository;
  private final CustomerService customerService;

  // 배송지 추가
  @PostMapping("/addAddress")
  public ResponseEntity<DeliveryAddressDto> addAddress(Authentication auth,
      @RequestBody AddAddressForm form) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(DeliveryAddressDto.from(customerService.addAddress(customerId, form)));
  }

  // 배송지 수정
  @PutMapping("/updateAddress")
  public ResponseEntity<DeliveryAddressDto> updateAddress(Authentication auth,
      @RequestBody @Valid UpdateDeliverAddressForm form) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(
        DeliveryAddressDto.from(customerService.updateAddress(customerId, form)));
  }

  // 배송지 삭제
  @DeleteMapping("/deleteAddress")
  public ResponseEntity<String> deleteAddress(Authentication auth, @RequestParam Long addressId) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    customerService.deleteAddress(customerId, addressId);
    return ResponseEntity.ok("삭제되었습니다");
  }

  // 배송지 확인
  @GetMapping("/getAddress")
  public ResponseEntity<AddressPageResponse> getAddress(Authentication auth,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize) {
    Long customerId = customRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(customerService.getAddress(customerId, pageNo, pageSize));
  }

}
