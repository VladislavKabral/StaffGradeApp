package by.kabral.usersservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Regex {
  public final String USER_NAME_REGEXP = "^[a-zA-Z- ]+$";
  public final String TEAM_NAME_REGEXP = "^[a-zA-Z0-9- ]+$";
  public final String POSITION_NAME_REGEXP = "^[a-zA-Z0-9- ]+$";
}
