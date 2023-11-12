package com.joonhyuk.Subject.commerce.domain.user.form;

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
public class UpdateInfoForm {

  @NotBlank(message = "닉네임이 입력되지 않았습니다.")
  String nickname;

  @NotBlank(message = "휴대폰번호가 입력되지 않았습니다.")
  String phone;

  @NotBlank(message = "비밃번호가 입력되지 않았습니다.")
  String password;

}
