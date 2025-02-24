package by.kabral.packagesservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Regex {
  public final String PACKAGE_NAME_REGEXP = "^[a-zA-Z0-9-() ]+$";
  public final String RESPONSE_BODY_REGEXP = "^[a-zA-Z0-9-,.!:; ]+$";
}
