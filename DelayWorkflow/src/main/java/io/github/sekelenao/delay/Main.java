package io.github.sekelenao.delay;

import io.github.sekelenao.delay.configuration.JobConfiguration;
import io.github.sekelenao.delay.kafka.KafkaSourceFactory;
import io.github.sekelenao.flinkboot.core.api.Flinkboot;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public final class Main {

    public static void main(String[] args) throws Exception {
        var flinkboot = Flinkboot.initialize(args);
        var configuration = flinkboot.configuration(JobConfiguration.class);
        var executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        var source = new KafkaSourceFactory(configuration).supply();

        var stream = executionEnvironment.fromSource(
            source,
            WatermarkStrategy.noWatermarks(),
            "Kafka Train Events Source"
        );

        stream.print();

        executionEnvironment.execute("DelayWorkflow");
    }

}

