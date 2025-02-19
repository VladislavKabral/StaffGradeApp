package by.kabral.formsservice.util.validator;

import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.model.Form;
import by.kabral.formsservice.repository.FormsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static by.kabral.formsservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class FormsValidator implements Validator<Form> {

  private final FormsRepository formsRepository;

  @Override
  public void validate(Form form) throws EntityValidateException {
    Optional<Form> exitingForm = formsRepository.findByName(form.getName());
    if ((exitingForm.isPresent()) && (!exitingForm.get().getId().equals(form.getId()))) {
      throw new EntityValidateException(String.format(FORM_WITH_GIVEN_NAME_ALREADY_EXISTS, form.getName()));
    }
  }
}
