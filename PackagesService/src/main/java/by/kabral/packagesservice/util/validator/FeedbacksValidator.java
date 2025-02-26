package by.kabral.packagesservice.util.validator;

import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.model.Feedback;
import by.kabral.packagesservice.repository.PackagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static by.kabral.packagesservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class FeedbacksValidator implements Validator<Feedback> {

  private final PackagesRepository packagesRepository;

  @Override
  public void validate(Feedback feedback) throws EntityValidateException {
    if (!packagesRepository.existsById(feedback.getThePackage().getId())) {
      throw new EntityValidateException(String.format(PACKAGE_NOT_FOUND, feedback.getThePackage().getId()));
    }
  }
}
