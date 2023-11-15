package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.components.KaKaoAddress;
import com.joonhyuk.Subject.commerce.domain.Customer.AddressPageResponse;
import com.joonhyuk.Subject.commerce.domain.Customer.DeliveryAddress;
import com.joonhyuk.Subject.commerce.domain.Customer.DeliveryAddressDto;
import com.joonhyuk.Subject.commerce.domain.Customer.form.AddAddressForm;
import com.joonhyuk.Subject.commerce.domain.Customer.form.UpdateDeliverAddressForm;
import com.joonhyuk.Subject.commerce.domain.cart.Cart;
import com.joonhyuk.Subject.commerce.domain.cart.CartDto;
import com.joonhyuk.Subject.commerce.domain.cart.CartPageResponse;
import com.joonhyuk.Subject.commerce.domain.cart.form.AddCartForm;
import com.joonhyuk.Subject.commerce.domain.cart.form.UpdateCartForm;
import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.product.ProductOption;
import com.joonhyuk.Subject.commerce.domain.repository.address.DeliverAddressRepository;
import com.joonhyuk.Subject.commerce.domain.repository.cart.CartRepository;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductOptionRepository;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final DeliverAddressRepository addressRepository;
  private final CartRepository cartRepository;
  private final ProductOptionRepository productOptionRepository;
  private final ProductRepository productRepository;


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


  // 장바구니에 상품 추가
  public Cart addCart(Long customerId, AddCartForm form) {
    Product product = productRepository.findById(form.getProductId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

    Cart cart = cartRepository.findByCustomerIdAndProductIdAndAndProductSize(customerId,
        form.getProductId(), form.getSize());

    ProductOption option = productOptionRepository.findByProductIdAndSize(form.getProductId()
        , form.getSize()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION));

    if (cart != null) {
      throw new CustomException(ErrorCode.ALREADY_EXIST_CART);
    }
    if (option.getCount() < form.getCount()) {
      throw new CustomException(ErrorCode.TOO_MANY_COUNT);
    }
    return cartRepository.save(Cart.of(customerId, form));
  }

  // 장바구니 상품 수정
  @Transactional
  public Cart updateCart(Long customerId, UpdateCartForm form) {
    Cart cart = cartRepository.findByIdAndCustomerId(form.getId(), customerId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART));

    ProductOption option = productOptionRepository.findByProductIdAndSize(cart.getProductId(),
        form.getSize()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION));

    if (option.getCount() < form.getCount()) {
      throw new CustomException(ErrorCode.TOO_MANY_COUNT);
    }
    cart.setProductCount(form.getCount());
    cart.setProductSize(form.getSize());

    return cart;
  }

  // 장바구니 삭제
  @Transactional
  public void deleteCart(Long customerId, Long cartId) {
    Cart cart = cartRepository.findByIdAndCustomerId(cartId, customerId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART));
    cartRepository.delete(cart);
  }

  // 장바구니 목록 확인
  @Cacheable(key = "#customerId", value = "cart", condition = "#customerId != null")
  public CartPageResponse getCart(Long customerId, int pageNo, int pageSize) {

    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<Cart> cartPage = cartRepository.findCartByCustomerId(customerId, pageable);

    if (cartPage.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_CART);
    }
    List<Cart> cartList = cartPage.getContent();

    return CartPageResponse.builder()
        .cartList(cartList.stream().map(cart -> CartDto.from(cart, productRepository))
            .collect(Collectors.toList()))
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(cartPage.getTotalElements())
        .totalPages(cartPage.getTotalPages())
        .last(cartPage.isLast())
        .build();

  }

}
