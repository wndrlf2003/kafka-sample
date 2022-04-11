package com.kafka.test.seriallize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kafka.test.event.dto.GenericKafkaEventDto;
import com.kafka.test.event.dto.KafkaEventDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author pyk@woowahan.com
 */
public class JacksonToJsonEventMessageConverter extends MessagingMessageConverter {

    private final JsonToKafkaEventDtoSerializer jsonToKafkaEventDtoSerializer;
    private final ObjectMapper objectMapper;

    public JacksonToJsonEventMessageConverter() {
        this(JsonToKafkaEventDtoSerializer.cbor(GenericKafkaEventDto.class));
    }

    public JacksonToJsonEventMessageConverter(JsonToKafkaEventDtoSerializer jsonToKafkaEventDtoSerializer) {
        Assert.notNull(jsonToKafkaEventDtoSerializer, "'eventSerializer' must not be null.");
        this.jsonToKafkaEventDtoSerializer = jsonToKafkaEventDtoSerializer;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected Object convertPayload(Message<?> message) {
        try {
            if (ClassUtils.isAssignableValue(KafkaEventDto.class, message.getPayload())) {
                return this.jsonToKafkaEventDtoSerializer.serialize((KafkaEventDto) message.getPayload());
            }
            return this.objectMapper.writeValueAsString(message.getPayload());
        } catch (JsonProcessingException error) {
            throw new ConversionException("Failed to convert to JSON", error);
        }
    }

    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
        Object value = record.value();
        if (record.value() == null) {
            return KafkaNull.INSTANCE;
        }

        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        if (value instanceof byte[]) {
            try {
                if (javaType.isTypeOrSubTypeOf(KafkaEventDto.class)) {
                    if (javaType.isInterface() || javaType.isAbstract()) {
                        return this.jsonToKafkaEventDtoSerializer.deserialize((byte[]) value);
                    }
                    return this.jsonToKafkaEventDtoSerializer.deserialize((byte[]) value, javaType);
                }
                return this.objectMapper.readValue((byte[]) value, javaType);
            } catch (Exception error) {
                throw new ConversionException("Failed to convert from JSON", error);
            }
        } else if (value instanceof String) {
            try {
                return this.objectMapper.readValue((String) value, javaType);
            } catch (IOException e) {
                throw new ConversionException("Failed to convert from JSON", e);
            }
        } else {
            throw new IllegalStateException("Only String or byte[] supported");
        }
    }

}
