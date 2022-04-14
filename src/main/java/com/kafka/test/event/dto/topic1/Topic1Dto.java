package com.kafka.test.event.dto.topic1;

import com.kafka.test.event.dto.GenericKafkaEventDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Topic1Dto extends GenericKafkaEventDto<TestData> {
    public Topic1Dto(String id, TestData data, Date createdDate) {
        super(id, data, createdDate);
    }

    public static Topic1Dto of (String id, TestData data) {
        return new Topic1Dto(
                id,
                data,
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        );
    }
}
