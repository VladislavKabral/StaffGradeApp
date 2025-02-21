package by.kabral.packagesservice.dto;

import by.kabral.packagesservice.model.Feedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.Constant.*;
import static by.kabral.packagesservice.util.Message.*;
import static by.kabral.packagesservice.util.Regex.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageDto {

  private UUID id;

  @Size(min = PACKAGE_NAME_MIN_SIZE, max = PACKAGE_NAME_MAX_SIZE, message = PACKAGE_NAME_SIZE_IS_INVALID)
  @Pattern(regexp = PACKAGE_NAME_REGEXP, message = PACKAGE_NAME_BODY_IS_INVALID)
  @NotBlank(message = PACKAGE_NAME_IS_EMPTY)
  private String name;

  @NotNull(message = PACKAGE_TARGET_USER_IS_NULL)
  private UserDto targetUser;

  @NotNull(message = PACKAGE_FORM_IS_NULL)
  private FormDto form;

  private Boolean isPublic;

  private LocalDate createdAt;

  private List<Feedback> feedbacks;
}
