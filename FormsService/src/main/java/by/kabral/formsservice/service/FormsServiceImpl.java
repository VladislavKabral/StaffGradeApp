package by.kabral.formsservice.service;

import by.kabral.formsservice.dto.FormDto;
import by.kabral.formsservice.dto.FormsListDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.mapper.FormsMapper;
import by.kabral.formsservice.model.Form;
import by.kabral.formsservice.repository.FormsRepository;
import by.kabral.formsservice.util.validator.FormsValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static by.kabral.formsservice.util.CircuitBreakerName.*;
import static by.kabral.formsservice.util.Message.*;
import static by.kabral.formsservice.util.RetryName.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "forms")
public class FormsServiceImpl implements EntitiesService<FormsListDto, Form, FormDto> {

  private final FormsRepository formsRepository;
  private final FormsMapper formsMapper;
  private final FormsValidator formsValidator;

  @Override
  @Transactional(readOnly = true)
  @Cacheable()
  public FormsListDto findAll() {
    return FormsListDto.builder()
            .forms(formsMapper.toListDto(formsRepository.findAll()))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public Form findEntity(UUID id) throws EntityNotFoundException {
    return formsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(FORM_NOT_FOUND, id)));
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(key = "#id", unless = "#result == null")
  public FormDto findById(UUID id) throws EntityNotFoundException {
    return formsMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = SAVE_FORM_BREAKER)
  @Retry(name = SAVE_FORM_RETRY)
  @CacheEvict(allEntries = true)
  public FormDto save(FormDto entity) throws EntityValidateException {
    Form form = formsMapper.toEntity(entity);
    formsValidator.validate(form);
    FormDto formDto = formsMapper.toDto(formsRepository.save(form));

    if (formDto.getId() == null) {
      throw new EntityValidateException(String.format(FORM_NOT_CREATED, form.getName()));
    }

    return formDto;
  }

  @Override
  @Transactional
  @CircuitBreaker(name = UPDATE_FORM_BREAKER)
  @Retry(name = UPDATE_FORM_RETRY)
  @CacheEvict(key = "#id")
  public FormDto update(UUID id, FormDto entity) throws EntityNotFoundException, EntityValidateException {
    if (!formsRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FORM_NOT_FOUND, id));
    }

    Form form = formsMapper.toEntity(entity);
    formsValidator.validate(form);

    return formsMapper.toDto(formsRepository.save(form));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = DELETE_FORM_BREAKER)
  @Retry(name = DELETE_FORM_RETRY)
  @CacheEvict(key = "#id")
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!formsRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FORM_NOT_FOUND, id));
    }

    formsRepository.deleteById(id);

    return id;
  }
}
