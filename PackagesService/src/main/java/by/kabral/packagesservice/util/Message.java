package by.kabral.packagesservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {

  public final String PACKAGE_NAME_SIZE_IS_INVALID = "The name of the package must be from 4 to 50 characters.";
  public final String PACKAGE_NAME_BODY_IS_INVALID = "Wrong package name.";
  public final String PACKAGE_NAME_IS_EMPTY = "The package name cannot be empty.";
  public final String PACKAGE_TARGET_USER_IS_NULL = "The target user cannot be null.";
  public final String PACKAGE_FORM_IS_NULL = "The form cannot be null.";
  public final String FEEDBACK_PACKAGE_IS_NULL = "The feedback's package cannot be null.";
  public final String FEEDBACK_SOURCE_USER_IS_NULL = "The feedback's source user cannot be null.";
  public final String RESPONSE_QUESTION_IS_NULL = "The response's question cannot be null.";
  public final String RESPONSE_RATE_IS_WRONG = "The response's rate isn't correct.";
  public final String RESPONSE_RATE_IS_NULL = "The response's rate cannot be null.";
  public final String RESPONSE_BODY_IS_EMPTY = "The response's body cannot be empty.";
  public final String RESPONSE_BODY_SIZE_IS_INVALID = "The body of the response must be form 4 to 250 characters.";
  public final String RESPONSE_BODY_IS_INVALID = "The response's body isn't correct.";
  public final String USER_ID_IS_NULL = "The user's id cannot be null.";
  public final String FORM_ID_IS_NULL = "The form's id cannot be null.";
  public final String QUESTION_ID_IS_NULL = "The question's id cannot be null.";
  public final String PACKAGE_ALREADY_EXISTS = "The package with name '%s' already exists";
  public final String PACKAGE_NOT_FOUND = "The package with id '%s' not found";
  public final String FEEDBACK_NOT_FOUND = "The feedback with id '%s' not found";
  public final String PACKAGE_NOT_CREATED = "The package with name '%s' not created";
  public final String FEEDBACK_NOT_CREATED = "The feedback with source user '%s' not created";
  public final String REQUEST_PARAMETER_IS_INVALID = "Failed to convert value in request parameter.";
  public final String METHOD_NOT_ALLOWED = "%s for this endpoint.";
  public final String EXTERNAL_SERVICE_IS_UNAVAILABLE = "Cannot connect to external service.";
  public final String TARGET_USER_ID_CANNOT_BE_NULL = "Target user's id cannot be null.";
}
