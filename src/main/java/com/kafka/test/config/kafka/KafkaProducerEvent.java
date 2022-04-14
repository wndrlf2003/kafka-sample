package com.kafka.test.config.kafka;

import com.kafka.test.event.dto.KafkaEventDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Getter
@Setter
@ToString
public class KafkaProducerEvent<E extends KafkaEventDto<?>> implements KafkaProducerSyncEvent<E>, KafkaProducerAsyncEvent<E>, InitializingBean {

    String topic;
    KafkaTemplate<String, E> kafkaTemplate;

    public KafkaProducerEvent(String topic, KafkaTemplate<String, E> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 기타설정
        log.info("KafkaProducerEvent.afterPropertiesSet()");
    }

    @Override
    public void sendAsyncKafkaMessage() {
        // 쓰래드 활용해서 재시도 정책과 함께 구현
    }

    @Override
    public void sendAsyncKafkaMessages() {
        // 쓰래드 활용해서 재시도 정책과 함께 구현
    }

    @Override
    public void sendKafkaMessage(E data) {
        kafkaTemplate.send(topic, data.getId(), data);
    }

    @Override
    public void sendKafkaMessages(Iterable<E> data) {
        data.forEach(d -> kafkaTemplate.send(topic, d.getId(), d));
    }
}
