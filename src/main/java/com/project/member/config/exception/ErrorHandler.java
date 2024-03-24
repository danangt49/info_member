package com.project.member.config.exception;

import com.project.member.config.GlobalApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException ce) {
    return GlobalApiResponse.builder()
        .message(ce.getMessage())
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .build()
        .entity();
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException ce) {
    return GlobalApiResponse.builder()
        .message(ce.getMessage())
        .status(ce.getHttpStatus().value())
        .error(ce.getHttpStatus().getReasonPhrase())
        .build()
        .entity();
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleAccessDeniedException() {
    return GlobalApiResponse.builder()
        .message(HttpStatus.FORBIDDEN.getReasonPhrase())
        .status(HttpStatus.FORBIDDEN.value())
        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
        .build()
        .entity();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception e) {
    return GlobalApiResponse.builder()
        .message(e.getMessage())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .build()
        .entity();
  }
}
