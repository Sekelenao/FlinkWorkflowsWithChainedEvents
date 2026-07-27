package io.github.sekelenao.delay.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.flinkboot.core.api.properties.JobProperties;
import io.github.sekelenao.flinkboot.kafka.api.properties.sink.KafkaSinkProperties;
import io.github.sekelenao.flinkboot.kafka.api.properties.source.KafkaSourceTopicListProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class JobConfiguration implements Serializable {

    @Valid
    @NotNull
    private final JobProperties job;

    @Valid
    @NotNull
    private final KafkaSourceTopicListProperties kafkaSource;

    @Valid
    @NotNull
    private final KafkaSinkProperties kafkaSink;

    @JsonCreator
    public JobConfiguration(
        @JsonProperty("job") JobProperties job,
        @JsonProperty("kafka-source") KafkaSourceTopicListProperties kafkaSource,
        @JsonProperty("kafka-sink") KafkaSinkProperties kafkaSink
    ) {
        this.job = job;
        this.kafkaSource = kafkaSource;
        this.kafkaSink = kafkaSink;
    }

    public JobProperties job() {
        return job;
    }

    public KafkaSourceTopicListProperties kafka() {
        return kafkaSource;
    }

    public KafkaSinkProperties kafkaSink() {
        return kafkaSink;
    }

}
