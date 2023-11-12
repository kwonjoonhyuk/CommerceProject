package com.joonhyuk.Subject.commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

  @ExceptionHandler({CustomException.class})
  public ResponseEntity<CustomException.CustomExceptionResponse> exceptionHandler(
      final CustomException customException) {

    return ResponseEntity
        .status(customException.getStatus())
        .body(CustomException.CustomExceptionResponse.builder()
            .message(customException.getMessage())
            .code(customException.getErrorCode().name())
            .status(customException.getStatus())
            .build());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ValidRestResponse> validException(
      MethodArgumentNotValidException exception) {

    ValidRestResponse response = new ValidRestResponse(HttpStatus.BAD_REQUEST.value(), "유효성 검사 실패",
        exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
