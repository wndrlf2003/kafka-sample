package com.kafka.test.producer;

import com.kafka.test.event.KafkaProducerSyncEvent;
import com.kafka.test.event.dto.KafkaEventDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class KafkaTopic1ProducerEvent {
    private final KafkaProducerSyncEvent<KafkaEventDto<?>> eventStore;

    public void save(KafkaEventDto<?> event) {
        eventStore.sendKafkaMessage(event);
    }
    public void saves(Iterable<KafkaEventDto<?>> event) {
        eventStore.sendKafkaMessages(event);
    }
}
