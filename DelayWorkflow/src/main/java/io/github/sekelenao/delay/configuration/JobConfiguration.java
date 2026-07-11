package io.github.sekelenao.delay.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.delay.configuration.part.KafkaConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JobConfiguration {

    @Valid
    @NotNull
    private final KafkaConfiguration kafka;

    @NotBlank
    private final String jobName;

    @JsonCreator
    public JobConfiguration(@JsonProperty("kafka") KafkaConfiguration kafka, @JsonProperty("job-name") String jobName) {
        this.kafka = kafka;
        this.jobName = jobName;
    }

    public KafkaConfiguration kafka() {
        return kafka;
    }

    public String jobName() {
        return jobName;
    }

}
