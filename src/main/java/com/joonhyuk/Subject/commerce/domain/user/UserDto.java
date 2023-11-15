package com.joonhyuk.Subject.commerce.domain.user;

import com.joonhyuk.Subject.commerce.domain.user.form.UpdateInfoForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String nickname;
  private String phone;
  private String password;

  public static UserDto from(User user) {
    return UserDto.builder()
        .id(user.getId())
        .nickname(user.getNickname())
        .phone(user.getPhone())
        .password(user.getPassword())
        .build();
  }

}
