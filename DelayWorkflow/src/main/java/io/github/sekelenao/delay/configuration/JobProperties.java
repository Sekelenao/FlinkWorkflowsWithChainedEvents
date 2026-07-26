package io.github.sekelenao.delay.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.flinkboot.core.api.configuration.JobConfiguration;
import io.github.sekelenao.flinkboot.kafka.api.configuration.source.KafkaSourceTopicListConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class JobProperties implements Serializable {

    @Valid
    @NotNull
    private final JobConfiguration job;

    @Valid
    @NotNull
    private final KafkaSourceTopicListConfiguration kafka;

    @JsonCreator
    public JobProperties(
        @JsonProperty("job") JobConfiguration job,
        @JsonProperty("kafka") KafkaSourceTopicListConfiguration kafka
    ) {
        this.job = job;
        this.kafka = kafka;
    }

    public JobConfiguration job() {
        return job;
    }

    public KafkaSourceTopicListConfiguration kafka() {
        return kafka;
    }

}
