package by.kabral.usersservice.service;

import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;

import java.util.UUID;

public interface EntitiesService<U, K, T, V> {
  U findAll();
  K findEntity(UUID id) throws EntityNotFoundException;
  T findById(UUID id) throws EntityNotFoundException;
  T save(V entity) throws EntityValidateException, EntityNotFoundException;
  T update(UUID id, V entity) throws EntityNotFoundException, EntityValidateException;
  UUID delete(UUID id) throws EntityNotFoundException;
}
