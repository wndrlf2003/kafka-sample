package com.kafka.test.event.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Topic1Dto extends GenericKafkaEventDto<Object> {
    public Topic1Dto(String id, Object data, Date createdDate) {
        super(id, data, createdDate);
    }

    public static Topic1Dto of (String id, Object data) {
        return new Topic1Dto(
                id,
                data,
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        );
    }
}
