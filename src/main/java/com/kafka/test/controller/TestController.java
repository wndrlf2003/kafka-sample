package com.kafka.test.controller;

import com.kafka.test.event.dto.topic1.TestData;
import com.kafka.test.event.dto.topic1.Topic1Dto;
import com.kafka.test.producer.KafkaTopic1ProducerEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TestController {
    private final KafkaTopic1ProducerEvent kafkaTopic1ProducerEvent;
    @GetMapping("/test1")
    public String test() {
        kafkaTopic1ProducerEvent.save(Topic1Dto.of("id", new TestData("1", "2", "3")));
        return "g";
    }
}
