package by.kabral.apigateway.controller;

import by.kabral.apigateway.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.kabral.apigateway.util.Message.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

  @GetMapping("/froms-service")
  public ResponseEntity<ErrorResponseDto> formsServiceFallback() {
    return new ResponseEntity<>(ErrorResponseDto.buildErrorResponse(FORMS_SERVICE_IS_UNAVAILABLE),
            HttpStatus.SERVICE_UNAVAILABLE);
  }

  @GetMapping("/packages-service")
  public ResponseEntity<ErrorResponseDto> packagesServiceFallback() {
    return new ResponseEntity<>(ErrorResponseDto.buildErrorResponse(PACKAGES_SERVICE_IS_UNAVAILABLE),
            HttpStatus.SERVICE_UNAVAILABLE);
  }

  @GetMapping("/users-service")
  public ResponseEntity<ErrorResponseDto> usersServiceFallback() {
    return new ResponseEntity<>(ErrorResponseDto.buildErrorResponse(USERS_SERVICE_IS_UNAVAILABLE),
            HttpStatus.SERVICE_UNAVAILABLE);
  }
}
