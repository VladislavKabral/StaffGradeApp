package by.kabral.packagesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackagesPageDto {

  private List<PackageDto> content;
  private int page;
  private int size;
}
