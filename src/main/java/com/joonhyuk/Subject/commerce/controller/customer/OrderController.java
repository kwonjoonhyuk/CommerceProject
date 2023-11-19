package com.joonhyuk.Subject.commerce.controller.customer;

import com.joonhyuk.Subject.commerce.aop.OrderLock;
import com.joonhyuk.Subject.commerce.domain.order.OrderDetailsPageResponse;
import com.joonhyuk.Subject.commerce.domain.order.OrderDto;
import com.joonhyuk.Subject.commerce.domain.order.form.AddOrderDetailsForm;
import com.joonhyuk.Subject.commerce.domain.order.form.CancelForm;
import com.joonhyuk.Subject.commerce.domain.repository.product.ProductRepository;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import com.joonhyuk.Subject.commerce.service.OrderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final UserCustomRepository userCustomRepository;
  private final OrderService orderService;
  private final ProductRepository productRepository;

  // 상품 결제하기(장바구니) 동시성 이슈 해결해보기
  @PostMapping
  @OrderLock
  public ResponseEntity<OrderDto> OrderProduct(Authentication auth,
      @RequestBody @Valid AddOrderDetailsForm form) {
    Long customerId = userCustomRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(
        OrderDto.from(orderService.orderProduct(customerId, form), productRepository));

  }

  // 상품 결제 취소하기(장바구니) 취소부분은 동시성 이슈 안해도될것 같음
  @PutMapping
  public ResponseEntity<OrderDto> Cancel(Authentication auth,
      @RequestBody @Valid CancelForm form) {
    Long customerId = userCustomRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(
        (OrderDto.from(orderService.cancel(customerId, form.getOrderDetailId()),
            productRepository)));
  }

  // 주문내역확인
  @GetMapping
  public ResponseEntity<OrderDetailsPageResponse> getOrderDetails(Authentication auth,
      @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
    Long customerId = userCustomRepository.findIdByEmail(auth.getName());
    return ResponseEntity.ok(orderService.getOrderDetails(customerId, pageNo, pageSize));
  }
}
