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
public class SkillDto implements Serializable {

  private UUID id;

  @Size(min = SKILL_NAME_MIN_SIZE, max = SKILL_NAME_MAX_SIZE, message = SKILL_NAME_SIZE_IS_INVALID)
  @Pattern(regexp = SKILL_NAME_REGEXP, message = SKILL_NAME_IS_INVALID)
  @NotEmpty(message = SKILL_NAME_IS_EMPTY)
  private String name;

  @Size(min = SKILL_DESCRIPTION_MIN_SIZE, max = SKILL_DESCRIPTION_MAX_SIZE, message = SKILL_DESCRIPTION_SIZE_IS_INVALID)
  @Pattern(regexp = SKILL_DESCRIPTION_REGEXP, message = SKILL_DESCRIPTION_IS_EMPTY)
  @NotEmpty(message = SKILL_DESCRIPTION_IS_INVALID)
  private String description;
}
