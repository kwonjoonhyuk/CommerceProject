package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.domain.cart.Cart;
import com.joonhyuk.Subject.commerce.domain.order.OrderDetails;
import com.joonhyuk.Subject.commerce.domain.order.OrderDetailsDto;
import com.joonhyuk.Subject.commerce.domain.order.OrderDetailsPageResponse;
import com.joonhyuk.Subject.commerce.domain.order.OrderDto;
import com.joonhyuk.Subject.commerce.domain.order.OrderStatusEnum;
import com.joonhyuk.Subject.commerce.domain.order.form.AddOrderDetailsForm;
import com.joonhyuk.Subject.commerce.domain.product.Product;
import com.joonhyuk.Subject.commerce.domain.product.ProductOption;
import com.joonhyuk.Subject.commerce.domain.repository.cart.CartRepository;
import com.joonhyuk.Subject.commerce.domain.repository.order.OrderDetailsRepository;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductOptionRepository;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.domain.user.User;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
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
public class OrderService {


  private final CartRepository cartRepository;
  private final ProductOptionRepository productOptionRepository;
  private final UserCustomRepository userRepository;
  private final ProductRepository productRepository;
  private final OrderDetailsRepository orderDetailsRepository;


  //상품 결제하기(장바구니)
  @Transactional
  public OrderDetails orderProduct(Long customerId, AddOrderDetailsForm form) {
    // 장바구니와 고객 아이디로 물품 확인
    Cart cart = cartRepository.findByIdAndCustomerId(form.getCartId(), customerId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART));

    // 상품 가격 비교를 위한 상품 가져오기
    Product product = productRepository.findById(cart.getProductId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

    // 주문하려는 물품의 사이즈가 존재하는지 여부확인
    ProductOption productOption = productOptionRepository.findByIdAndSize(
            form.getOptionId(), form.getSize())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION));

    // 주문하려는 사이즈의 개수가 남아있는지 여부 확인
    if (productOption.getCount() == 0) {
      throw new CustomException(ErrorCode.ZERO_COUNT_OPTION);
    }
    // 주문하려는 사이즈의 개수가 남아있는 개수보다 큰지 작은지 여부 확인
    if (form.getCount() > productOption.getCount()) {
      throw new CustomException(ErrorCode.TOO_MANY_COUNT);
    }
    // 잔액 확인
    User user = userRepository.findById(customerId);
    Integer totalPrice = product.getPrice() * form.getCount();
    if (user.getBalance() < totalPrice) {
      throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
    }
    // 결제성공시 장바구니에서 상품 삭제됨
    cartRepository.delete(cart);

    // 결제 성공시 옵션 재고 차감 및 사용자 잔액 가격만큼 차감
    user.minusBalance(totalPrice);

    productOption.minusCount(form.getCount());

    // 결제 성공시 결제내역 저장
    return orderDetailsRepository.save(
        OrderDetails.of(customerId, form, totalPrice, product.getId()));
  }

  // Productoption 카운트 뺄때 lock 걸어서 못쓰게함

  // 상품 결제하기 취소 및 환불
  @Transactional
  public OrderDetails cancel(Long customerId, Long orderDetailsId) {
    OrderDetails orderDetails = orderDetailsRepository.findByIdAndCustomerId(orderDetailsId,
            customerId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER_DETAILS));

    if (orderDetails.getStatus().equals(OrderStatusEnum.결제취소)) {
      throw new CustomException(ErrorCode.ALREADY_CANCEL);
    }
    ProductOption option = productOptionRepository.findById(orderDetails.getOptionId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION));
    // 환불 성공시 상품 옵션 사이즈의 개수 다시 반환
    option.plusCount(orderDetails.getCount());
    // 환불 성공시 잔액 잔고 플러스
    User user = userRepository.findById(customerId);
    user.plusBalance(orderDetails.getTotalPrice());
    // 환불시에 orderDetails에 status 결제취소로 변경
    orderDetails.setStatus(OrderStatusEnum.결제취소);

    return orderDetails;
  }


  // 주문 내역 확인 수정된 순(최근순)
  public OrderDetailsPageResponse getOrderDetails(Long customerId, int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<OrderDetails> orderDetailsPage = orderDetailsRepository.findOrderDetailsByCustomerIdOrderByModifiedAtDesc(
        customerId, pageable);
    if (orderDetailsPage.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_ORDER_DETAILS);
    }
    List<OrderDetails> orderDetailsList = orderDetailsPage.getContent();

    return OrderDetailsPageResponse.builder()
        .orderDetailsDtoList(orderDetailsList.stream()
            .map(orderDetails -> OrderDetailsDto.from(orderDetails, productRepository)).collect(
                Collectors.toList()))
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(orderDetailsPage.getTotalElements())
        .totalPages(orderDetailsPage.getTotalPages())
        .last(orderDetailsPage.isLast())
        .build();
  }


}
