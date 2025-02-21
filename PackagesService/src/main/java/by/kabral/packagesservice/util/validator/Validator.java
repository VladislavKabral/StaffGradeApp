package by.kabral.packagesservice.util.validator;

import by.kabral.packagesservice.exception.EntityValidateException;

public interface Validator<T> {
  void validate(T t) throws EntityValidateException;
}
