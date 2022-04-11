package com.kafka.test.event;

import com.kafka.test.event.dto.KafkaEventDto;

public interface KafkaProducerAsyncEvent<E extends KafkaEventDto<?>> {
    void sendAsyncKafkaMessage();
    void sendAsyncKafkaMessages();
}
