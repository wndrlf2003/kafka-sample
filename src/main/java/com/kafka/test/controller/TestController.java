package com.kafka.test.controller;

import com.kafka.test.event.dto.Topic1Dto;
import com.kafka.test.producer.KafkaTopic1ProducerEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@Slf4j
@RestController
@AllArgsConstructor
public class TestController {
    private final KafkaTopic1ProducerEvent kafkaTopic1ProducerEvent;
    @GetMapping("/test1")
    public String test() {
        kafkaTopic1ProducerEvent.save(Topic1Dto.of("id", new testData("1", "2", "3")));
        return "g";
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class testData implements Serializable {
        private String data1;
        private String data2;
        private String data3;

        public testData() {

        }

        @Override
        public String toString() {
            return "data1: " + data1 + ", data2: " + data2 + ", data3: " + data3;
        }
    }
}
