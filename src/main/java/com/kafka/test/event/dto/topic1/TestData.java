package com.kafka.test.event.dto.topic1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TestData {
    private String data1;
    private String data2;
    private String data3;

    public TestData() {

    }

    @Override
    public String toString() {
        return "data1: " + data1 + ", data2: " + data2 + ", data3: " + data3;
    }
}
