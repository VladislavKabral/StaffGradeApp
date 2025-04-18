package by.kabral.usersservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

  @Value("${spring.kafka.users-topic-name}")
  private String usersTopicName;

  @Bean
  public NewTopic usersTopic() {
    return TopicBuilder.name(usersTopicName).partitions(1).build();
  }
}
