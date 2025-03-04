package by.kabral.formsservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static by.kabral.formsservice.util.Constant.*;
import static by.kabral.formsservice.util.Message.*;
import static by.kabral.formsservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormDto implements Serializable {

  private UUID id;

  @Size(min = FORM_NAME_MIN_SIZE, max = FORM_NAME_MAX_SIZE, message = FORM_NAME_SIZE_IS_INVALID)
  @Pattern(regexp = FORM_NAME_REGEXP, message = FORM_NAME_BODY_IS_INVALID)
  @NotEmpty(message = FORM_NAME_IS_EMPTY)
  private String name;

  private List<QuestionDto> questions;
}
