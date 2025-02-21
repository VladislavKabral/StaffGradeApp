package by.kabral.packagesservice.client;

import by.kabral.packagesservice.config.FeignClientConfig;
import by.kabral.packagesservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "${feign.client.config.users.name}",
        configuration = FeignClientConfig.class,
        path = "${feign.client.config.users.path}")
public interface UsersFeignClient {

  @GetMapping("/{id}")
  ResponseEntity<UserDto> getUserById(@PathVariable("id") UUID id);
}
