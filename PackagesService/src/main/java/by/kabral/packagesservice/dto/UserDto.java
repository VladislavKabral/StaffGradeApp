package by.kabral.packagesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import static by.kabral.packagesservice.util.Message.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

  @NotNull(message = USER_ID_IS_NULL)
  private UUID id;
  private String lastname;
  private String firstname;
  private String email;
  private UserDto manager;
  private PositionDto position;
  private TeamDto team;

}
