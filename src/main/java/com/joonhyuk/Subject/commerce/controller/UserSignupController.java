package com.joonhyuk.Subject.commerce.controller;

import com.joonhyuk.Subject.commerce.domain.user.SignupForm;
import com.joonhyuk.Subject.commerce.service.UserSignUpService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class UserSignupController {

  private final UserSignUpService userSignUpService;

  //고객 회원가입 진행(정보입력 후 이메일 인증 발송)
  @PostMapping("/customer")
  public ResponseEntity<String> signupCustomer(@RequestBody SignupForm form) {
    return ResponseEntity.ok(userSignUpService.signup(form, "customer"));
  }

  // 고객 회원가입 진행 이메일 인증
  @GetMapping("/customer/verify")
  public ResponseEntity<String> verifyEmailCustomer(String email, String code) {
    userSignUpService.verifyEmail(email, code);
    return ResponseEntity.ok( userSignUpService.verifyEmail(email, code));
  }

  // 판매자 회원가입 진행(정보입력 후 이메일 인증 발송)
  @PostMapping("/seller")
  public ResponseEntity<String> signupSeller(@RequestBody SignupForm form) {
    return ResponseEntity.ok(userSignUpService.signup(form, "seller"));
  }

  // 판매자 회원가입 진행 이메일 인증
  @GetMapping("/seller/verify")
  public ResponseEntity<String> verifyEmailSeller(String email, String code) {

    return ResponseEntity.ok( userSignUpService.verifyEmail(email, code));
  }

}
