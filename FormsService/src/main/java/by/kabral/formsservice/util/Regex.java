package by.kabral.formsservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Regex {
  public final String FORM_NAME_REGEXP = "^[a-zA-Z0-9- ]+$";
  public final String QUESTION_TEXT_REGEXP = "^[a-zA-Z0-9-,.?: ]+$";
  public final String SKILL_NAME_REGEXP = "^[a-zA-Z- ]+$";
  public final String SKILL_DESCRIPTION_REGEXP = "^[a-zA-Z0-9-,.?: ]+$";
}
