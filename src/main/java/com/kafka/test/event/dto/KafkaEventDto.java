package com.kafka.test.event.dto;


import java.io.Serializable;
import java.util.Date;

public interface KafkaEventDto<T> extends Serializable {
    String getId();
    T getData();
    Date getCreatedTime();
}
