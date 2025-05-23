package by.kabral.formsservice.service;

import by.kabral.formsservice.dto.SkillDto;
import by.kabral.formsservice.dto.SkillsListDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.mapper.SkillsMapper;
import by.kabral.formsservice.model.Skill;
import by.kabral.formsservice.repository.SkillsRepository;
import by.kabral.formsservice.util.validator.SkillsValidator;
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
@CacheConfig(cacheNames = "skills")
public class SkillsServiceImpl implements EntitiesService<SkillsListDto, Skill, SkillDto> {

  private final SkillsRepository skillsRepository;
  private final SkillsMapper skillsMapper;
  private final SkillsValidator skillsValidator;

  @Override
  @Transactional(readOnly = true)
  @Cacheable()
  public SkillsListDto findAll() {
    return SkillsListDto.builder()
            .skills(skillsMapper.toListDto(skillsRepository.findAll()))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public Skill findEntity(UUID id) throws EntityNotFoundException {
    return skillsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(SKILL_NOT_FOUND, id)));
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(key = "#id", unless = "#result == null")
  public SkillDto findById(UUID id) throws EntityNotFoundException {
    return skillsMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = SAVE_SKILL_BREAKER)
  @Retry(name = SAVE_SKILL_RETRY)
  @CacheEvict(allEntries = true)
  public SkillDto save(SkillDto entity) throws EntityValidateException {
    Skill skill = skillsMapper.toEntity(entity);
    skillsValidator.validate(skill);
    SkillDto savedSkill = skillsMapper.toDto(skillsRepository.save(skill));

    if (savedSkill.getId() == null) {
      throw new EntityValidateException(String.format(SKILL_NOT_CREATED, skill.getName()));
    }

    return savedSkill;
  }

  @Override
  @Transactional
  @CircuitBreaker(name = UPDATE_SKILL_BREAKER)
  @Retry(name = UPDATE_SKILL_RETRY)
  @CacheEvict(key = "#id")
  public SkillDto update(UUID id, SkillDto entity) throws EntityNotFoundException, EntityValidateException {
    if (!skillsRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(SKILL_NOT_FOUND, id));
    }

    Skill skill = skillsMapper.toEntity(entity);
    skillsValidator.validate(skill);
    return skillsMapper.toDto(skillsRepository.save(skill));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = DELETE_SKILL_BREAKER)
  @Retry(name = DELETE_SKILL_RETRY)
  @CacheEvict(key = "#id")
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!skillsRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(SKILL_NOT_FOUND, id));
    }

    skillsRepository.deleteById(id);
    return id;
  }
}
