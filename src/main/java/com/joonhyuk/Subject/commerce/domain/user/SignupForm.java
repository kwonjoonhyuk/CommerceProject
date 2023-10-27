package com.joonhyuk.Subject.commerce.domain.user;

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
public class SignupForm {
  private String email;
  private String name;
  private String password;
  private String nick;
  private String phone;
}
