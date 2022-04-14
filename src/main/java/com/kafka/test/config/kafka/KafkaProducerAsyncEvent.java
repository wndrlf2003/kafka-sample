package com.kafka.test.config.kafka;

import com.kafka.test.event.dto.KafkaEventDto;

public interface KafkaProducerAsyncEvent<E extends KafkaEventDto<?>> {
    void sendAsyncKafkaMessage();
    void sendAsyncKafkaMessages();
}
