package by.kabral.packagesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.Message.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormDto implements Serializable {

  @NotNull(message = FORM_ID_IS_NULL)
  private UUID id;
  private String name;
  private List<QuestionDto> questions;
}
