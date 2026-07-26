package io.github.sekelenao.producers.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public class JsonSerializer implements Serializer<Object> {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public byte[] serialize(String topic, Object data) {
        Objects.requireNonNull(data);
        try {
            return mapper.writeValueAsBytes(data);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to serialize object to JSON", e);
        }
    }

}
