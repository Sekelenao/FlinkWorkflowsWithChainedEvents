package io.github.sekelenao.producers.serde;

import org.apache.kafka.common.serialization.Serializer;
import tools.jackson.databind.json.JsonMapper;

import java.util.Objects;

public class JsonSerializer implements Serializer<Object> {

    private final JsonMapper mapper = new JsonMapper();

    @Override
    public byte[] serialize(String topic, Object data) {
        Objects.requireNonNull(data);
        return mapper.writeValueAsBytes(data);
    }

}
