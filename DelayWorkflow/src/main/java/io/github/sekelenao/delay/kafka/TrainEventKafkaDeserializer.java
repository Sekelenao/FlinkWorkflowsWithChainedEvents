package io.github.sekelenao.delay.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class TrainEventKafkaDeserializer implements KafkaRecordDeserializationSchema<TrainEvent> {

    private static final long serialVersionUID = 1L;

    private transient ObjectMapper mapper;

    private ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }
        return mapper;
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<TrainEvent> out) {
        try {
            var event = getMapper().readValue(consumerRecord.value(), TrainEvent.class);
            out.collect(event);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to deserialize TrainEvent from Kafka record", e);
        }
    }

    @Override
    public TypeInformation<TrainEvent> getProducedType() {
        return TypeInformation.of(TrainEvent.class);
    }

}
