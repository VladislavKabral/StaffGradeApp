package by.kabral.formsservice.service;

import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;

import java.util.UUID;

public interface EntitiesService<U, K, T> {
  U findAll();
  K findEntity(UUID id) throws EntityNotFoundException;
  T findById(UUID id) throws EntityNotFoundException;
  T save(T entity) throws EntityValidateException;
  T update(UUID id, T entity) throws EntityNotFoundException, EntityValidateException;
  UUID delete(UUID id) throws EntityNotFoundException;
}
