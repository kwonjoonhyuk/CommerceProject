package com.joonhyuk.Subject.commerce.aop;

import com.joonhyuk.Subject.commerce.domain.repository.user.UserRepository;
import com.joonhyuk.Subject.commerce.domain.user.User;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserClassificationAop {

  private final UserRepository userRepository;

  // 범위 지정 sellerController에서만
  @Pointcut("execution(* com.joonhyuk.Subject.commerce.controller.SellerController.*(..)) ")
  public void controller() {
  }

  @Pointcut("execution(* com.joonhyuk.Subject.commerce.controller.customer..*(..))")
  public void pointCut() {
  }


  @Before("pointCut()")
  public void BeforeCustomer(JoinPoint joinPoint) {
    final UsernamePasswordAuthenticationToken token;
    Object[] args = joinPoint.getArgs();

    token = (UsernamePasswordAuthenticationToken) args[0];
    User user = userRepository.findUserByEmail(token.getPrincipal().toString());

    if (user.getRole().equals("판매자")) {
      throw new CustomException(ErrorCode.DO_NOT_ACCESS_RIGHTS);
    }
  }


  @Before("controller() ")
  public void Before(JoinPoint joinPoint) {
    final UsernamePasswordAuthenticationToken token;
    Object[] args = joinPoint.getArgs();

    token = (UsernamePasswordAuthenticationToken) args[0];
    User user = userRepository.findUserByEmail(token.getPrincipal().toString());

    if (user.getRole().equals("고객")) {
      throw new CustomException(ErrorCode.DO_NOT_ACCESS_RIGHTS);
    }
  }

}
