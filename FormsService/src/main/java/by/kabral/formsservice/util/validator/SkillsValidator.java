package by.kabral.formsservice.util.validator;

import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.model.Skill;
import by.kabral.formsservice.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static by.kabral.formsservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class SkillsValidator implements Validator<Skill> {

  private final SkillsRepository skillsRepository;

  @Override
  public void validate(Skill skill) throws EntityValidateException {
    Optional<Skill> existingSkill = skillsRepository.findByName(skill.getName());
    if ((existingSkill.isPresent()) && (!existingSkill.get().getId().equals(skill.getId()))) {
      throw new EntityValidateException(String.format(SKILL_WITH_GIVEN_NAME_ALREADY_EXISTS, skill.getName()));
    }
  }
}
