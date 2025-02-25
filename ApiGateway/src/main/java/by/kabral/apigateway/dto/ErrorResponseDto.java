package by.kabral.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {

  private String message;
  private LocalDateTime timestamp;

  public static ErrorResponseDto buildErrorResponse(String message) {
    return ErrorResponseDto.builder()
            .message(message)
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
            .build();
  }
}
