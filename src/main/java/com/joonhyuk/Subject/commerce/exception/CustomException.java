package com.joonhyuk.Subject.commerce.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
  private final int status;
  private static final ObjectMapper mappper = new ObjectMapper();

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getDatail());
    this.errorCode = errorCode;
    this.status = errorCode.getHttpStatus().value();
  }

  @AllArgsConstructor
  @Builder
  @NoArgsConstructor
  @Getter
  public static class CustomExceptionResponse{
    private int status;
    private String code;
    private String message;
  }
}
