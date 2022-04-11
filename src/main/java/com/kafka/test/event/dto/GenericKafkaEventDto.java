package com.kafka.test.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
public class GenericKafkaEventDto<T> implements KafkaEventDto<T>, Serializable {
    String id;
    T data;
    @JsonFormat
    Date createdDate;

    public GenericKafkaEventDto() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Date getCreatedTime() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "id: " + id + ", data: " + data + ", createdDate: " + createdDate;
    }
}
