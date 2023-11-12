package com.joonhyuk.Subject.commerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST,"이미 가입되어 있는 회원입니다."),
  ALREADY_EXIST_PHONE(HttpStatus.BAD_REQUEST,"이미 존재하는 휴대폰 번호입니다."),
  ALREADY_VERIFY(HttpStatus.BAD_REQUEST,"이미 인증이 완료된 이메일 주소입니다."),
  EXPIRED_CODE(HttpStatus.BAD_REQUEST,"인증시간이 만료되었습니다."),
  WRONG_VERIFICATION(HttpStatus.BAD_REQUEST,"잘못된 인증 시도입니다."),
  NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"해당 회원을 찾을 수 없습니다."),
  ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST,"이미 존재하는 닉네임입니다.");



  private final HttpStatus httpStatus;
  private final String datail;
}
