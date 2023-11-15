package com.joonhyuk.Subject.commerce.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
  결제완료("결제완료"),
  결제취소("결제취소(환불)");

  private final String type;

  OrderStatusEnum(String type) {
    this.type = type;
  }
}
