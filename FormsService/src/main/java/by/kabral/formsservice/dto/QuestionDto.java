package by.kabral.formsservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import static by.kabral.formsservice.util.Constant.*;
import static by.kabral.formsservice.util.Message.*;
import static by.kabral.formsservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto implements Serializable {

  private UUID id;

  @Size(min = QUESTION_TEXT_MIN_SIZE, max = QUESTION_TEXT_MAX_SIZE, message = QUESTION_TEXT_SIZE_IS_INVALID)
  @Pattern(regexp = QUESTION_TEXT_REGEXP, message = QUESTION_TEXT_BODY_IS_INVALID)
  @NotEmpty(message = QUESTION_TEXT_IS_EMPTY)
  private String text;

  @NotEmpty(message = QUESTION_SKILL_IS_EMPTY)
  private SkillDto skill;
}
