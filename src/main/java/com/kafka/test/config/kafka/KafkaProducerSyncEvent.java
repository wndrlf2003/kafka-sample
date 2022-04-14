package com.kafka.test.config.kafka;

import com.kafka.test.event.dto.KafkaEventDto;

public interface KafkaProducerSyncEvent<E extends KafkaEventDto<?>> {
    void sendKafkaMessage(E data);
    void sendKafkaMessages(Iterable<E> events);
}
