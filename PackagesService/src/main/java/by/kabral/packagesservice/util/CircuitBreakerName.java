package by.kabral.packagesservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CircuitBreakerName {

  public final String SAVE_FEEDBACK_BREAKER = "saveFeedbackCircuitBreaker";
  public final String COMPLETE_FEEDBACK_BREAKER = "completeFeedbackCircuitBreaker";
  public final String UPDATE_FEEDBACK_BREAKER = "updateFeedbackCircuitBreaker";
  public final String DELETE_FEEDBACK_BREAKER = "deleteFeedbackCircuitBreaker";
  public final String SAVE_PACKAGE_BREAKER = "savePackageCircuitBreaker";
  public final String UPDATE_PACKAGE_BREAKER = "updatePackageCircuitBreaker";
  public final String DELETE_PACKAGE_BREAKER = "deletePackageCircuitBreaker";
}
