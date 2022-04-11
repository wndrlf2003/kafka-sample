package com.kafka.test.producer;

import com.kafka.test.event.KafkaProducerAsyncEvent;
import com.kafka.test.event.KafkaProducerSyncEvent;
import com.kafka.test.event.dto.KafkaEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class NOProducerEvent implements KafkaProducerSyncEvent<KafkaEventDto<?>>, KafkaProducerAsyncEvent<KafkaEventDto<?>>, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("NOProducerEvent.afterPropertiesSet()");
    }

    @Override
    public void sendAsyncKafkaMessage() {
        log.info("NOProducerEvent.sendAsyncKafkaMessage()");
    }

    @Override
    public void sendAsyncKafkaMessages() {
        log.info("NOProducerEvent.sendAsyncKafkaMessages()");
    }

    @Override
    public void sendKafkaMessage(KafkaEventDto<?> data) {
        log.info("NOProducerEvent.sendKafkaMessage()");
    }

    @Override
    public void sendKafkaMessages(Iterable<KafkaEventDto<?>> events) {
        log.info("NOProducerEvent.sendKafkaMessages()");
        System.out.println("aaaaaa");
    }
}
