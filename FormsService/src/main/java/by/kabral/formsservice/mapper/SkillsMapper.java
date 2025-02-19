package by.kabral.formsservice.mapper;

import by.kabral.formsservice.dto.SkillDto;
import by.kabral.formsservice.model.Skill;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkillsMapper {

  private final ModelMapper modelMapper;

  public Skill toEntity(SkillDto skillDto) {
    return modelMapper.map(skillDto, Skill.class);
  }

  public SkillDto toDto(Skill skill) {
    return modelMapper.map(skill, SkillDto.class);
  }

  public List<SkillDto> toListDto(List<Skill> skills) {
    return skills.stream().map(this::toDto).toList();
  }
}
