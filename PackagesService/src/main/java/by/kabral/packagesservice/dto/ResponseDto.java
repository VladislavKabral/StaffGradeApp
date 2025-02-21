package by.kabral.packagesservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static by.kabral.packagesservice.util.Constant.*;
import static by.kabral.packagesservice.util.Message.*;
import static by.kabral.packagesservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

  private UUID id;

  @NotNull(message = RESPONSE_QUESTION_IS_NULL)
  private QuestionDto question;

  @DecimalMin(value = RESPONSE_RATE_MIN_VALUE, message = RESPONSE_RATE_IS_WRONG)
  @DecimalMax(value = RESPONSE_RATE_MAX_VALUE, message = RESPONSE_RATE_IS_WRONG)
  @NotNull(message = RESPONSE_RATE_IS_NULL)
  private Double rate;

  @NotBlank(message = RESPONSE_BODY_IS_EMPTY)
  @Size(min = RESPONSE_BODY_MIN_SIZE, max = RESPONSE_BODY_MAX_SIZE , message = RESPONSE_BODY_SIZE_IS_INVALID)
  @Pattern(regexp = RESPONSE_BODY_REGEXP, message = RESPONSE_BODY_IS_INVALID)
  private String text;
}
