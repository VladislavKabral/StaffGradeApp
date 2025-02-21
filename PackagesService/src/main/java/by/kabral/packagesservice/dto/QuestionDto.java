package by.kabral.packagesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static by.kabral.packagesservice.util.Message.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {

  @NotNull(message = QUESTION_ID_IS_NULL)
  private UUID id;
  private String text;
  private SkillDto skill;
}
