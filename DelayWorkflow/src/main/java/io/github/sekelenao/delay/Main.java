package io.github.sekelenao.delay;

import io.github.sekelenao.delay.configuration.JobProperties;
import io.github.sekelenao.delay.kafka.TrainEventKafkaDeserializer;
import io.github.sekelenao.delay.operator.DumbMapFunction;
import io.github.sekelenao.flinkboot.core.api.Flinkboot;
import io.github.sekelenao.flinkboot.kafka.api.source.KafkaSourceFactory;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public final class Main {

    public static void main(String[] args) throws Exception {
        var flinkboot = Flinkboot.initialize(args);
        var configuration = flinkboot.configuration(JobProperties.class);
        var executionEnvironment = flinkboot.executionEnvironment(configuration.job());
        var source = KafkaSourceFactory.supplyFor(configuration.kafka(), new TrainEventKafkaDeserializer());
        var stream = executionEnvironment.fromSource(
            source,
            WatermarkStrategy.noWatermarks(),
            "Kafka Train Events Source"
        );
        stream.map(new DumbMapFunction(configuration.job().name())).print();
        executionEnvironment.execute("DelayWorkflow");
    }

}

