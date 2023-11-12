package com.joonhyuk.Subject.commerce.exception;

import lombok.Getter;

@Getter
public class ValidRestResponse {

  private final int status;
  private final String success;
  private final String message;

  public ValidRestResponse(int status, String success, String message) {
    this.status = status;
    this.success = success;
    this.message = message;
  }
}
