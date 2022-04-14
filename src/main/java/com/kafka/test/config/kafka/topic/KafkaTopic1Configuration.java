package com.kafka.test.config.kafka.topic;

import com.kafka.test.producer.KafkaTopic1ProducerEvent;
import com.kafka.test.producer.NOProducerEvent;
import com.kafka.test.config.kafka.KafkaEventFactoryBeen;
import com.kafka.test.config.kafka.KafkaProducerEvent;
import com.kafka.test.seriallize.JacksonToJsonEventMessageConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

@Slf4j
@Setter
@Configuration
@ConfigurationProperties(prefix = "sample.kafka.topic1")
public class KafkaTopic1Configuration {
    private boolean enabled;
    private String topic;

    @Bean
    public KafkaTopic1ProducerEvent kafkaTopic1ProducerEvent(KafkaProperties kafkaProperties) throws Exception {
        if (enabled) {
            KafkaEventFactoryBeen kafkaEventFactoryBeen = new KafkaEventFactoryBeen();
            kafkaEventFactoryBeen.setBootstrapServers(collectionToCommaDelimitedString(kafkaProperties.getBootstrapServers()));
            kafkaEventFactoryBeen.setTopicName(topic);
            kafkaEventFactoryBeen.afterPropertiesSet();

            KafkaProducerEvent kafkaProducerEvent = kafkaEventFactoryBeen.getObject();
            return new KafkaTopic1ProducerEvent(kafkaProducerEvent);
        } else {
            return new KafkaTopic1ProducerEvent(new NOProducerEvent());
        }
    }

    @Bean("KAFKA_TOPIC1_CONSUMER_FACTORY")
    public KafkaListenerContainerFactory kafkaTopic1ConsumerFactory(KafkaProperties kafkaProperties) {
        DefaultKafkaConsumerFactory<String, byte[]> consumerFactory = new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties()); {
            consumerFactory.setKeyDeserializer(new StringDeserializer());
            consumerFactory.setValueDeserializer(new ByteArrayDeserializer());
        }
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>(); {
            factory.setConsumerFactory(consumerFactory);
            factory.setMessageConverter(new JacksonToJsonEventMessageConverter());
        }
        return factory;
    }
}
