package com.kafka.test.seriallize;

import com.fasterxml.jackson.databind.JavaType;
import com.kafka.test.event.dto.KafkaEventDto;
import org.apache.kafka.common.errors.SerializationException;

public interface KafkaEventDtoSerializer <E extends KafkaEventDto<?>> {
    byte[] serialize(E KafkaEventDto) throws SerializationException;
    E deserialize(byte[] b) throws SerializationException;
    E deserialize(byte[] b, JavaType javaType) throws SerializationException;
}
