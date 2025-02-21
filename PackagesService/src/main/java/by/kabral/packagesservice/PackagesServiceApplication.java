package by.kabral.packagesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PackagesServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PackagesServiceApplication.class, args);
  }

}
