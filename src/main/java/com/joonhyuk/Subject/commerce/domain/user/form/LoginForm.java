package com.joonhyuk.Subject.commerce.domain.user.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class LoginForm {

  @Email(message = "이메일 형식이 아닙니다.")
  String email;
  @NotBlank(message = "패스워드를 입력해주세요")
  String password;

}
