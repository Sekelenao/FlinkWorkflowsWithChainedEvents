package io.github.sekelenao.delay;

import io.github.sekelenao.delay.configuration.JobConfiguration;
import io.github.sekelenao.delay.event.TrainEvent;
import io.github.sekelenao.delay.kafka.TrainEventKafkaDeserializer;
import io.github.sekelenao.delay.kafka.TrainEventSerializationSchema;
import io.github.sekelenao.delay.operator.DumbMapFunction;
import io.github.sekelenao.flinkboot.core.api.Flinkboot;
import io.github.sekelenao.flinkboot.kafka.api.sink.KafkaSinkFactory;
import io.github.sekelenao.flinkboot.kafka.api.source.KafkaSourceFactory;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;

public final class Main {

    public static void main(String[] args) throws Exception {
        var flinkboot = Flinkboot.initialize(args);
        var configuration = flinkboot.configuration(JobConfiguration.class);
        var executionEnvironment = flinkboot.executionEnvironment(configuration.job());

        var source = KafkaSourceFactory.supplyFor(configuration.kafka(), new TrainEventKafkaDeserializer());

        var serializationSchema = KafkaRecordSerializationSchema.<TrainEvent>builder()
                .setTopic(configuration.kafkaSink().topic())
                .setValueSerializationSchema(new TrainEventSerializationSchema())
                .build();
        var sink = KafkaSinkFactory.supplyFor(configuration.kafkaSink(), serializationSchema);

        var stream = executionEnvironment.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Train Events Source");
        var mappedStream = stream.map(new DumbMapFunction(configuration.job().name()));
        mappedStream.sinkTo(sink);

        executionEnvironment.execute("DelayWorkflow");
    }

}

