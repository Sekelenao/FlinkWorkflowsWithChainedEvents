package io.github.sekelenao.delay.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.api.common.serialization.SerializationSchema;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class TrainEventSerializationSchema implements SerializationSchema<TrainEvent> {

    private static final long serialVersionUID = 1L;

    private transient ObjectMapper mapper;

    @Override
    public void open(InitializationContext context) {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public byte[] serialize(TrainEvent element) {
        if (element == null) {
            return new byte[0];
        }
        try {
            return mapper.writeValueAsBytes(element);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to serialize TrainEvent to JSON", e);
        }
    }
}
