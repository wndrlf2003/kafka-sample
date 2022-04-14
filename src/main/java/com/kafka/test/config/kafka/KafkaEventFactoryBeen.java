package com.kafka.test.config.kafka;

import com.kafka.test.event.dto.GenericKafkaEventDto;
import com.kafka.test.event.dto.KafkaEventDto;
import com.kafka.test.seriallize.JsonToKafkaEventDtoSerializer;
import com.kafka.test.seriallize.WrapperSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

@Slf4j
public class KafkaEventFactoryBeen <E extends KafkaEventDto<?>> implements FactoryBean<KafkaProducerEvent<E>>, InitializingBean {

    KafkaProducerEvent<E> kafkaProducerEvent;
    Properties producerProperties;
    private String topic;

    public KafkaEventFactoryBeen() {
        producerProperties = new Properties();
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5 * 1000);
        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5 * 1000);
    }

    @Override
    public KafkaProducerEvent<E> getObject() throws Exception {
        return kafkaProducerEvent;
    }

    @Override
    public Class<?> getObjectType() {
        return KafkaProducerEvent.class;
    }

    public KafkaEventFactoryBeen setBootstrapServers(String bootstrapServers) {
        producerProperties.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return this;
    }

    public KafkaEventFactoryBeen setTopicName(String topic) {
        this.topic = topic;
        return this;
    }

    public void setKeySerializer(Serializer<E> serializer) {

    }

    public void setValueSerializer(Serializer<E> serializer) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("KafkaEventFactoryBeen.afterPropertiesSet()");

        DefaultKafkaProducerFactory<String, E> kafkaProducerFactory = new DefaultKafkaProducerFactory(new HashMap<>(producerProperties));

        // serialize 셋팅.
        kafkaProducerFactory.setKeySerializer(new StringSerializer());
        JsonToKafkaEventDtoSerializer serializer = JsonToKafkaEventDtoSerializer.cbor(GenericKafkaEventDto.class);
        kafkaProducerFactory.setValueSerializer(new WrapperSerializer<E>(serializer));

        KafkaTemplate<String, E> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        kafkaProducerEvent = new KafkaProducerEvent<>(topic, kafkaTemplate);
    }
}
