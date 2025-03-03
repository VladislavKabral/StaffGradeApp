package by.kabral.usersservice.kafka;

import by.kabral.usersservice.dto.TargetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {

  private final KafkaTemplate<String, TargetUserDto> kafkaTemplate;

  public void sendRequestWithNewTargetUser(TargetUserDto targetUserDto, String topicName) {
    kafkaTemplate.send(topicName, targetUserDto);
  }
}
