package com.kafka.test.seriallize;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.kafka.test.event.dto.KafkaEventDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.lang.Nullable;

import static java.util.Objects.isNull;

@Slf4j
public class JsonToKafkaEventDtoSerializer <E extends KafkaEventDto<?>> implements KafkaEventDtoSerializer<E> {
    JavaType javaType;
    ObjectMapper objectMapper;

    public JsonToKafkaEventDtoSerializer(Class<E> dtoClass, ObjectMapper objectMapper) {
        this.javaType = TypeFactory.defaultInstance().constructType(dtoClass);;
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(E kafkaEventDto) throws SerializationException {
        if (isNull(kafkaEventDto)) {
            return new byte[0];
        }

        try {
            return objectMapper.writeValueAsBytes(kafkaEventDto);
        } catch (Exception e) {
            throw new SerializationException("JsonToKafkaEventDtoSerializer.serialize error: " + e.getMessage(), e);
        }
    }

    @Nullable
    @Override
    public E deserialize(byte[] b) throws SerializationException {
        if (isNull(b) || b.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(b, javaType);
        } catch (Exception e) {
            throw new SerializationException("JsonToKafkaEventDtoSerializer.deserialize error: " + e.getMessage(), e);
        }
    }

    public E deserialize(byte[] b, JavaType javaType) throws SerializationException {
        if (isNull(b) || b.length == 0) {
            return null;
        }
        try {
            return this.objectMapper.readValue(b, 0, b.length, javaType);
        } catch (Exception e) {
            throw new SerializationException("JsonToKafkaEventDtoSerializer.deserialize error: " + e.getMessage(), e);
        }
    }

    // cbor factory ?????? ?????????
    public static <E extends KafkaEventDto<?>> JsonToKafkaEventDtoSerializer<E> cbor(Class<E> dtoClass) {
        // SimpleModule customModule = new SimpleModule();
        // customModule.addSerializer(?????????, ???????????????????????????)
        // customModule.addSerializer(?????????, ??????????????????????????????)
        ObjectMapper objectMapper = JsonMapper.builder(new CBORFactory())   // CBORFactory ??????
        //        .addModule(customModule) >> key ????????? ????????? ??? ?????? ??????
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false) // @JsonView??? ???????????? ?????? POJO??? ?????????????????? JSON ???????????? ?????? ??????
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // ????????? property??? ?????? ???????????? ????????????. DTO??? ?????? ????????? ????????? ????????????.
                .build();

        return new JsonToKafkaEventDtoSerializer<E>(dtoClass, objectMapper);
    }
}
