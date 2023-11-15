package com.joonhyuk.Subject.commerce.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

  int code;
  String message;

  public static MessageDto of(int code, String message) {

    return MessageDto.builder()
        .code(code)
        .message(message)
        .build();
  }
}
