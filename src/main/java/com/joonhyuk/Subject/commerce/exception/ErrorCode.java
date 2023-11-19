package com.joonhyuk.Subject.commerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 회원입니다."),
  ALREADY_EXIST_PHONE(HttpStatus.BAD_REQUEST, "이미 존재하는 휴대폰 번호입니다."),
  NOT_INPUT_ADDRESS(HttpStatus.BAD_REQUEST, "주소가 입력되지 않았습니다."),
  ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료된 이메일 주소입니다."),
  EXPIRED_CODE(HttpStatus.BAD_REQUEST, "인증시간이 만료되었습니다."),
  RESTART_VERIFY_CHANGE_PW(HttpStatus.BAD_REQUEST, "다시 인증부터 진행해주세요"),
  ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
  WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
  NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "해당 회원을 찾을 수 없습니다."),
  PHONE_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "가입하신 핸드폰번호가 일치하지 않습니다."),
  NAME_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "가입하신 이름과 일치하지 않습니다."),

  PASSWORD_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

  //유저 정보 변경 exception
  PLEASE_NEW_NICKNAME(HttpStatus.BAD_REQUEST, "기존 닉네임과 일치합니다. 새로운 닉네임을 입력해주세요"),
  PLEASE_NEW_PW(HttpStatus.BAD_REQUEST, "기존 비밀번호와 일치합니다. 새로운 비밀번호를 입력해주세요"),
  PLEASE_NEW_PHONE(HttpStatus.BAD_REQUEST, "기존 휴대폰번호와 일치합니다. 새로운 휴대폰번호를 입력해주세요"),


  // 상품에 대한 exception
  NOT_FOUND_OPTION(HttpStatus.BAD_REQUEST, "해당 상품에 대한 옵션을 찾을 수 없습니다."),
  DO_NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "해당 상품을 등록한 사용자와 다릅니다. 다시 시도해주세요"),
  INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다"),
  ALREADY_CANCEL(HttpStatus.BAD_REQUEST, "이미 취소된 거래건입니다."),
  DO_NOT_MATCH_USER_OPTION(HttpStatus.BAD_REQUEST, "해당 상품옵션을 등록한 사용자와 다릅니다. 다시 시도해주세요"),
  NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "해당 상품을 찾을 수 없습니다."),
  NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST, "해당하는 리뷰를 찾을수 없습니다."),
  ZERO_COUNT_OPTION(HttpStatus.BAD_REQUEST, "해당 사이즈의 상품은 품절되었습니다."),
  ALREADY_EXIST_SIZE(HttpStatus.BAD_REQUEST, "이미 존재하는 상품옵션 사이즈입니다."),
  PLEASE_NEW_COUNT(HttpStatus.BAD_REQUEST, "이미 해당하는 상품옵션 개수와 일치합니다."),

  OPTION_LOCK(HttpStatus.BAD_REQUEST, "Locking"),
  SERVER_ERROR(HttpStatus.BAD_REQUEST, "예기치 못한 서버 오류가 발생했습니다. 다시 시도해주세요"),

  // 판매자, 구매자 페이지 권한 설정 예외처리
  DO_NOT_ACCESS_RIGHTS(HttpStatus.BAD_REQUEST, "접근 권한이 없습니다."),

  //장바구니 exception
  ALREADY_EXIST_CART(HttpStatus.BAD_REQUEST, "장바구니에 이미 존재하는 물품입니다. 확인해주세요"),
  NOT_FOUND_ORDER_DETAILS(HttpStatus.BAD_REQUEST, "해당 거래내역이 존재하지 않습니다."),
  DO_NOT_WRITE_REVIEW(HttpStatus.BAD_REQUEST, "취소된 거래건에 대해서는 리뷰를 작성할 수 없습니다."),
  TOO_MANY_COUNT(HttpStatus.BAD_REQUEST, "선택하신 옵션의 개수보다 남아있는 상품의 개수가 적습니다."),
  NOT_FOUND_CART(HttpStatus.BAD_REQUEST, "해당 장바구니 물품을 찾을수 없습니다."),

  // jwt 토큰 exception
  TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "로그인 토큰이 만료되었습니다."),
  NOT_LOGIN_TOKEN(HttpStatus.BAD_REQUEST, "로그인이 필요합니다."),
  DO_NOT_MATCH_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 일치하지 않습니다."),
  ALREADY_LOGOUT_USER(HttpStatus.BAD_REQUEST, "이미 로그아웃된 계정이거나 토큰이 만료되었습니다. 다시 로그인해주세요"),
  TOKEN_INCORRECT(HttpStatus.BAD_REQUEST, "올바르지 않은 토큰 형식입니다.");


  private final HttpStatus httpStatus;
  private final String datail;
}
