package by.kabral.usersservice.dto;

import by.kabral.usersservice.model.Position;
import by.kabral.usersservice.model.Status;
import by.kabral.usersservice.model.Team;
import by.kabral.usersservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static by.kabral.usersservice.util.Constant.*;
import static by.kabral.usersservice.util.Message.*;
import static by.kabral.usersservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  private UUID id;

  @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = USER_LASTNAME_SIZE_IS_INVALID)
  @Pattern(regexp = USER_NAME_REGEXP, message = USER_LASTNAME_BODY_IS_INVALID)
  @NotEmpty(message = USER_LASTNAME_IS_EMPTY)
  private String lastname;

  @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = USER_FIRSTNAME_SIZE_IS_INVALID)
  @Pattern(regexp = USER_NAME_REGEXP, message = USER_FIRSTNAME_BODY_IS_INVALID)
  @NotEmpty(message = USER_FIRSTNAME_IS_EMPTY)
  private String firstname;

  @Email(message = USER_EMAIL_WRONG_FORMAT)
  @NotEmpty(message = USER_EMAIL_IS_EMPTY)
  private String email;

  private User manager;

  private Status status;

  private Position position;

  private Team team;
}
