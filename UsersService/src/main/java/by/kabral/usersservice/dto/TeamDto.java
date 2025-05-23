package by.kabral.usersservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import static by.kabral.usersservice.util.Constant.*;
import static by.kabral.usersservice.util.Message.*;
import static by.kabral.usersservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto implements Serializable {

  private UUID id;

  @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = TEAM_NAME_SIZE_IS_INVALID)
  @Pattern(regexp = TEAM_NAME_REGEXP, message = TEAM_NAME_BODY_IS_INVALID)
  @NotEmpty(message = TEAM_NAME_IS_EMPTY)
  private String name;
}
