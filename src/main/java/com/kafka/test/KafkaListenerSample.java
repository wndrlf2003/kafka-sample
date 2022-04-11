package com.kafka.test;


import com.kafka.test.event.dto.KafkaEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerSample {
    @KafkaListener(topics = "#{'${sample.kafka.topic1.topic}'.split(',')}", groupId = "topic1-test-group", containerFactory = "KAFKA_TOPIC1_CONSUMER_FACTORY")
    public void topic1Listener(Message<KafkaEventDto<?>> message) {
        log.info("message: " + message.getPayload());
    }
}
