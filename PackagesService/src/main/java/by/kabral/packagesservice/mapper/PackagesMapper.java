package by.kabral.packagesservice.mapper;

import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.model.Package;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PackagesMapper {

  private final FeedbacksMapper feedbacksMapper;

  public Package toEntity(PackageDto packageDto) {
    Package thePackage = new Package();
    thePackage.setId(packageDto.getId());
    thePackage.setName(packageDto.getName());
    thePackage.setFeedbacks(feedbacksMapper.toEntityList(packageDto.getFeedbacks()));
    thePackage.setTargetUserId(packageDto.getTargetUser().getId());
    thePackage.setFormId(packageDto.getForm().getId());
    thePackage.setPublic(packageDto.getIsPublic() == null);
    return thePackage;
  }
}
