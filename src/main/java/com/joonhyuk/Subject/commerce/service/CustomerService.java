package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.components.KaKaoAddress;
import com.joonhyuk.Subject.commerce.domain.Customer.AddressPageResponse;
import com.joonhyuk.Subject.commerce.domain.Customer.DeliveryAddress;
import com.joonhyuk.Subject.commerce.domain.Customer.DeliveryAddressDto;
import com.joonhyuk.Subject.commerce.domain.Customer.form.AddAddressForm;
import com.joonhyuk.Subject.commerce.domain.Customer.form.UpdateDeliverAddressForm;
import com.joonhyuk.Subject.commerce.domain.repository.address.DeliverAddressRepository;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final DeliverAddressRepository addressRepository;


  // 배송지 추가
  public DeliveryAddress addAddress(Long customerId, AddAddressForm form) {
    return addressRepository.save(DeliveryAddress.of(customerId, form));
  }

  // 배송지 수정
  @Transactional
  public DeliveryAddress updateAddress(Long customerId, UpdateDeliverAddressForm form) {
    DeliveryAddress address = addressRepository.findByCustomerIdAndId(customerId, form.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

    final KaKaoAddress kaKaoAddress = new KaKaoAddress();
    ArrayList<String> arrayList = kaKaoAddress.getLocation(form.getAddress());

    address.setAddress(arrayList.get(0));
    address.setAddressDetail(form.getAddress_detail());
    address.setZoneNo(arrayList.get(2));

    return address;
  }

  // 배송지 삭제
  public void deleteAddress(Long customerId, Long addressId) {
    DeliveryAddress address = addressRepository.findByCustomerIdAndId(customerId, addressId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    addressRepository.delete(address);
  }

  //배송지 확인(페이징 처리)
  public AddressPageResponse getAddress(Long customerId, int pageNo, int pageSize) {

    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<DeliveryAddress> addressPage = addressRepository.findDeliveryAddressByCustomerId(
        customerId, pageable);
    List<DeliveryAddress> deliveryAddressList = addressPage.getContent();
    return AddressPageResponse.builder()
        .addressList(
            deliveryAddressList.stream().map(DeliveryAddressDto::from).collect(Collectors.toList()))
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(addressPage.getTotalElements())
        .totalPages(addressPage.getTotalPages())
        .last(addressPage.isLast())
        .build();
  }

}
