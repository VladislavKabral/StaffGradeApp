package by.kabral.packagesservice.client;

import by.kabral.packagesservice.config.FeignClientConfig;
import by.kabral.packagesservice.dto.FormDto;
import by.kabral.packagesservice.dto.QuestionIdsListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "${feign.client.config.forms.name}",
        configuration = FeignClientConfig.class,
        path = "${feign.client.config.forms.path}")
public interface FormsFeignClient {

  @GetMapping("/{id}")
  ResponseEntity<FormDto> getFormById(@PathVariable("id") UUID id);

  @PostMapping("/questions/exists")
  ResponseEntity<Boolean> checkExists(@RequestBody QuestionIdsListDto questionIdsListDto);
}
