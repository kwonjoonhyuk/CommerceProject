package com.joonhyuk.Subject.commerce.service;


import static org.assertj.core.api.Assertions.assertThat;


import com.joonhyuk.Subject.commerce.domain.user.form.SignupForm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserSignUpServiceTest {

  @Autowired
  private UserSignUpService userSignUpService;

  @Test
  @DisplayName("회원가입 인증")
  void signUpCustomer() {

    //given
    SignupForm form = SignupForm.builder()
        .email("jkatwxc2@naver.com")
        .name("권준혁")
        .password("1")
        .nick("푸른")
        .phone("01012345678")
        .build();

    String text = userSignUpService.signup(form, "customer");
    assertThat(text).isEqualTo("인증 메일이 발송되었습니다. 이메일을 확인해주세요");

    //when
    //then
  }


  @Test
  @DisplayName("이메일 인증")
  void validateEmail(){

    String text = userSignUpService.verifyEmail("jkatwxc2@naver.com", "3gCBYt5dqY");
    assertThat(text).isEqualTo("이메일 인증 완료되었습니다.");

  }
}