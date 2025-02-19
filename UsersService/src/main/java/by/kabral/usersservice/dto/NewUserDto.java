package by.kabral.usersservice.dto;

import by.kabral.usersservice.model.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static by.kabral.usersservice.util.Constant.*;
import static by.kabral.usersservice.util.Message.*;
import static by.kabral.usersservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserDto {

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

  @NotEmpty(message = USER_PASSWORD_IS_EMPTY)
  private String password;

  private UserDto manager;

  private PositionDto position;

  private Status status;

  private TeamDto team;
}
