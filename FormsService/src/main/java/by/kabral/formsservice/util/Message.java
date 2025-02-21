package by.kabral.formsservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {

  public final String FORM_NAME_SIZE_IS_INVALID = "Form's name size must be between 4 and 50 characters";
  public final String QUESTION_TEXT_SIZE_IS_INVALID = "Question's body size must be between 4 and 250 characters";
  public final String SKILL_NAME_SIZE_IS_INVALID = "Skill's name size must be between 4 and 25 characters";
  public final String SKILL_DESCRIPTION_SIZE_IS_INVALID = "Skill's description size must be between 4 and 50 characters";
  public final String FORM_NAME_IS_EMPTY = "Form's name is empty";
  public final String QUESTION_TEXT_IS_EMPTY = "Question's body is empty";
  public final String QUESTION_SKILL_IS_EMPTY = "Question's skill is empty";
  public final String SKILL_NAME_IS_EMPTY = "Skill's name is empty";
  public final String SKILL_DESCRIPTION_IS_EMPTY = "Skill's description is empty";
  public final String FORM_NAME_BODY_IS_INVALID = "Form's name is invalid";
  public final String QUESTION_TEXT_BODY_IS_INVALID = "Question's body is invalid";
  public final String SKILL_NAME_IS_INVALID = "Skill's name is invalid";
  public final String SKILL_DESCRIPTION_IS_INVALID = "Skill's description is invalid";
  public final String FORM_WITH_GIVEN_NAME_ALREADY_EXISTS = "The form with name '%s' already exists";
  public final String SKILL_WITH_GIVEN_NAME_ALREADY_EXISTS = "The skill with name '%s' already exists";
  public final String FORM_NOT_FOUND = "The form with id '%s' not found";
  public final String SKILL_NOT_FOUND = "The skill with id '%s' not found";
  public final String QUESTION_NOT_FOUND = "The question with id '%s' not found";
  public final String FORM_NOT_CREATED = "The form with name '%s' not created";
  public final String SKILL_NOT_CREATED = "The skill with name '%s' not created";
  public final String QUESTION_NOT_CREATED = "The question with body '%s' not created";
  public final String EXTERNAL_SERVICE_IS_UNAVAILABLE = "Cannot connect to external service.";
  public final String METHOD_NOT_ALLOWED = "%s for this endpoint.";
  public final String REQUEST_PARAMETER_IS_INVALID = "Failed to convert value in request parameter.";
}
