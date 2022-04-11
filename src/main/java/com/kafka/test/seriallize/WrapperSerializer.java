package com.kafka.test.seriallize;

import com.kafka.test.event.dto.KafkaEventDto;
import org.apache.kafka.common.serialization.Serializer;

public class WrapperSerializer <E extends KafkaEventDto<?>> implements Serializer<E> {
    KafkaEventDtoSerializer<E> kafkaEventDtoSerializer;

    public WrapperSerializer(KafkaEventDtoSerializer<E> kafkaEventDtoSerializer) {
        this.kafkaEventDtoSerializer = kafkaEventDtoSerializer;
    }

    @Override
    public byte[] serialize(String topic, E data) {
        return kafkaEventDtoSerializer.serialize(data);
    }
}
