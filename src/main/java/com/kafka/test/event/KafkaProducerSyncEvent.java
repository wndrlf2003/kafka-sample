package com.kafka.test.event;

import com.kafka.test.event.dto.KafkaEventDto;

import java.util.List;

public interface KafkaProducerSyncEvent<E extends KafkaEventDto<?>> {
    void sendKafkaMessage(E data);
    void sendKafkaMessages(Iterable<E> events);
}
