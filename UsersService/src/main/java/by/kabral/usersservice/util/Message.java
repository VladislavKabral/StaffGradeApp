package by.kabral.usersservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {

  public final String INVALID_PAGE_REQUEST = "The number of page and count of elements can't be less than zero.";
  public final String USER_NOT_FOUND = "The user with id '%s' wasn't found.";
  public final String TEAM_NOT_FOUND = "The team with id '%s' wasn't found.";
  public final String POSITION_NOT_FOUND = "The position with id '%s' wasn't found.";
  public final String STATUS_NOT_FOUND = "The status with id '%s' wasn't found.";
  public final String USER_NOT_CREATED = "The user with email '%s' wasn't created.";
  public final String TEAM_NOT_CREATED = "The team with name '%s' wasn't created.";
  public final String POSITION_NOT_CREATED = "The position with name '%s' wasn't created.";
  public final String USER_LASTNAME_SIZE_IS_INVALID = "The user's lastname must be between 2 and 50 symbols.";
  public final String TEAM_NAME_SIZE_IS_INVALID = "Team's name must be between 2 and 50 symbols.";
  public final String POSITION_NAME_SIZE_IS_INVALID = "Position's lastname must be between 2 and 50 symbols.";
  public final String USER_LASTNAME_BODY_IS_INVALID = "User's lastname must contain only letters.";
  public final String TEAM_NAME_BODY_IS_INVALID = "Wrong format of team's name.";
  public final String POSITION_NAME_BODY_IS_INVALID = "Wrong format of position's name.";
  public final String USER_LASTNAME_IS_EMPTY = "User's lastname must be not empty.";
  public final String TEAM_NAME_IS_EMPTY = "User's lastname must be not empty.";
  public final String POSITION_NAME_IS_EMPTY = "Position's lastname must be not empty.";
  public final String USER_FIRSTNAME_SIZE_IS_INVALID = "User's firstname must be between 2 and 50 symbols.";
  public final String USER_FIRSTNAME_BODY_IS_INVALID = "User's firstname must contain only letters.";
  public final String USER_FIRSTNAME_IS_EMPTY = "User's firstname must be not empty.";
  public final String USER_EMAIL_WRONG_FORMAT = "Wrong email format.";
  public final String USER_EMAIL_IS_EMPTY = "User's email must be not empty.";
  public final String USER_PASSWORD_IS_EMPTY = "User's password must be not empty.";
  public final String METHOD_NOT_ALLOWED = "%s for this endpoint.";
  public final String REQUEST_PARAMETER_IS_INVALID = "Failed to convert value in request parameter.";
  public final String USER_WITH_GIVEN_EMAIL_ALREADY_EXISTS = "The user with email '%s' already exists.";
  public final String POSITION_WITH_GIVEN_NAME_ALREADY_EXISTS = "The position with name '%s' already exists.";
  public final String TEAM_WITH_GIVEN_NAME_ALREADY_EXISTS = "The team with name '%s' already exists.";
  public final String FIND_ALL_USERS = "Finding all users.";
  public final String RECEIVED_PAGE_PARAMETERS_ARE_INVALID = "Invalid page parameters were received";
  public final String FIND_USERS = "Finding users from the given page";
  public final String FIND_USER_BY_ID = "Finding the user with id '%d'";
  public final String SAVE_NEW_USER = "Saving a new user";
  public final String USER_WAS_SAVED = "The user '%s' '%s' was saved.";
  public final String UPDATE_USER = "Updating the user with id '%d'";
  public final String USER_WAS_UPDATED = "The user with id '%d' was updated.";
  public final String DEACTIVATE_USER = "Deactivating a user with id '%d'";
  public final String USER_WAS_DEACTIVATED = "The user with id '%d' was deactivated.";
  public final String FIND_USER_PROFILE = "Finding a profile of the user with id '%d'";
  public final String CREATING_USER_ALREADY_EXISTS = "Creating user already exists (existing parameter is '%s')";
  public final String EXTERNAL_SERVICE_IS_UNAVAILABLE = "Cannot connect to external service.";
  public final String UNKNOWN_USER_STATUS_NAME = "'%s' is unknown status";

}
