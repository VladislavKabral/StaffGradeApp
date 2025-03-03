package by.kabral.packagesservice.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Constant {

  public final int PACKAGE_NAME_MIN_SIZE = 4;
  public final int PACKAGE_NAME_MAX_SIZE = 50;
  public final String RESPONSE_RATE_MIN_VALUE = "0.0";
  public final String RESPONSE_RATE_MAX_VALUE = "10.0";
  public final int RESPONSE_BODY_MIN_SIZE = 4;
  public final int RESPONSE_BODY_MAX_SIZE = 250;
  public final long RETRYER_PERIOD = 100L;
  public final long RETRYER_MAX_PERIOD = 1000L;
  public final int RETRYER_MAX_ATTEMPTS = 5;
  public final int SERVER_ERROR_MIN_STATUS_CODE = 500;
  public final String COMMUNICATION_SKILLS_FORM_NAME = "%s %s (Communication skills)";
  public final UUID COMMUNICATION_SKILLS_FORM_UUID = UUID.fromString("b180f894-b722-436a-87f0-bf2bd1482c6e");
  public final String UTC_ZONE_NAME = "UTC";
}
