package com.joonhyuk.Subject.commerce.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

  @ExceptionHandler({CustomException.class})
  public ResponseEntity<CustomException.CustomExceptionResponse> exceptionHandler(
      HttpServletRequest request, final CustomException customException) {

    return ResponseEntity
        .status(customException.getStatus())
        .body(CustomException.CustomExceptionResponse.builder()
            .message(customException.getMessage())
            .code(customException.getErrorCode().name())
            .status(customException.getStatus())
            .build());
  }

}
