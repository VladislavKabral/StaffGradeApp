package by.kabral.usersservice.util.validator;

import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;

public interface Validator<T> {
  void validate(T t) throws EntityValidateException, EntityNotFoundException;
}
