package by.kabral.packagesservice.client;

import by.kabral.packagesservice.config.FeignClientConfig;
import by.kabral.packagesservice.dto.FormDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "${feign.client.config.forms.name}",
        configuration = FeignClientConfig.class,
        path = "${feign.client.config.forms.path}")
public interface FormsFeignClient {

  @GetMapping("/{id}")
  ResponseEntity<FormDto> getFormById(@PathVariable("id") UUID id);
}
