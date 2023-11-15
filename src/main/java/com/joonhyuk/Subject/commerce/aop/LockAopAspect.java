package com.joonhyuk.Subject.commerce.aop;

import com.joonhyuk.Subject.commerce.domain.order.form.AddOrderDetailsForm;
import com.joonhyuk.Subject.commerce.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {

  private final LockService lockService;

  @Around("@annotation(com.joonhyuk.Subject.commerce.aop.OrderLock))")
  public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object[] args = proceedingJoinPoint.getArgs();
    AddOrderDetailsForm form = (AddOrderDetailsForm) args[1];
    lockService.Lock(form.getOptionId());
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      lockService.unLock(form.getOptionId());
    }
  }
}
