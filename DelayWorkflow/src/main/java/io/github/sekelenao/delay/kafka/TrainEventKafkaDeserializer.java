package io.github.sekelenao.delay.kafka;

import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import tools.jackson.databind.json.JsonMapper;

public final class TrainEventKafkaDeserializer implements KafkaRecordDeserializationSchema<TrainEvent> {

    private final JsonMapper mapper = new JsonMapper();

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<TrainEvent> out) {
        var event = mapper.readValue(consumerRecord.value(), TrainEvent.class);
        out.collect(event);
    }

    @Override
    public TypeInformation<TrainEvent> getProducedType() {
        return TypeInformation.of(TrainEvent.class);
    }

}
