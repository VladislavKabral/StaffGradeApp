package by.kabral.packagesservice.dto;

import by.kabral.packagesservice.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.Message.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDto implements Serializable {

  private UUID id;

  @JsonIgnore
  @NotNull(message = FEEDBACK_PACKAGE_IS_NULL)
  private PackageDto thePackage;

  @NotNull(message = FEEDBACK_SOURCE_USER_IS_NULL)
  private UserDto sourceUser;

  private Status status;

  private LocalDate completedAt;

  private List<ResponseDto> responses;
}
