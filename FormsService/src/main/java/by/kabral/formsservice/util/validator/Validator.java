package by.kabral.formsservice.util.validator;

import by.kabral.formsservice.exception.EntityValidateException;

public interface Validator<T> {
  void validate(T t) throws EntityValidateException;
}
