package by.kabral.formsservice.controller.advice;

import by.kabral.formsservice.dto.ErrorResponseDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.exception.ExternalServiceRequestException;
import by.kabral.formsservice.exception.ExternalServiceUnavailableException;
import by.kabral.formsservice.exception.InvalidRequestDataException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static by.kabral.formsservice.util.Message.*;


@ControllerAdvice
public class ExceptionAPIController {

  @ExceptionHandler(value = {
          ExternalServiceRequestException.class,
          ExternalServiceUnavailableException.class,
          InvalidRequestDataException.class,
          EntityValidateException.class,
          HttpMessageNotReadableException.class,
          UnsatisfiedServletRequestParameterException.class,
          PropertyReferenceException.class
  })
  public ResponseEntity<ErrorResponseDto> defaultMessageExceptionHandler(Exception exception) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto.builder()
                    .message(exception.getMessage())
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> entityNotFoundException(EntityNotFoundException exception) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponseDto.builder()
                    .message(exception.getMessage())
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
    StringBuilder errorMessage = new StringBuilder();

    exception.getBindingResult()
            .getAllErrors()
            .forEach(error -> errorMessage.append(error.getDefaultMessage()).append(" "));

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto.builder()
                    .message(errorMessage.toString().trim())
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDto> methodArgumentTypeMismatchException() {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto.builder()
                    .message(REQUEST_PARAMETER_IS_INVALID)
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponseDto> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
    return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ErrorResponseDto.builder()
                    .message(String.format(METHOD_NOT_ALLOWED, exception.getMessage()))
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }

  @ExceptionHandler(ConnectException.class)
  public ResponseEntity<ErrorResponseDto> connectException() {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponseDto.builder()
                    .message(EXTERNAL_SERVICE_IS_UNAVAILABLE)
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());

  }

  @ExceptionHandler(CallNotPermittedException.class)
  public ResponseEntity<ErrorResponseDto> callNotPermittedException() {
    return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ErrorResponseDto.builder()
                    .message(REQUEST_CANNOT_BE_PERFORMED)
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }
}
