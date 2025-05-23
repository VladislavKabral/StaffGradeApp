package by.kabral.packagesservice.controller.advice;

import by.kabral.packagesservice.dto.ErrorResponseDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.exception.ExternalServiceRequestException;
import by.kabral.packagesservice.exception.ExternalServiceUnavailableException;
import by.kabral.packagesservice.exception.InvalidRequestDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.SneakyThrows;
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

import static by.kabral.packagesservice.util.Message.*;

@ControllerAdvice
public class ExceptionApiController {

  @ExceptionHandler(value = {
          ExternalServiceRequestException.class,
          ExternalServiceUnavailableException.class,
          InvalidRequestDataException.class,
          EntityValidateException.class,
          HttpMessageNotReadableException.class,
          UnsatisfiedServletRequestParameterException.class,
          PropertyReferenceException.class,
          RuntimeException.class
  })
  public ResponseEntity<ErrorResponseDto> defaultMessageExceptionHandler(Exception exception) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto.builder()
                    .message(exception.getMessage())
                    .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
                    .build());
  }

  @SneakyThrows
  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ErrorResponseDto> feignExceptionHandler(FeignException exception) {
    ObjectMapper mapper = new ObjectMapper();
    String message = mapper.readTree(exception.contentUTF8()).get("message").asText();

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto.builder()
                    .message(message)
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
}
