package io.github.sekelenao.delay.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.delay.configuration.part.KafkaConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class JobConfiguration {

    @Valid
    @NotNull
    private final KafkaConfiguration kafka;

    @JsonCreator
    public JobConfiguration(@JsonProperty("kafka") KafkaConfiguration kafka) {
        this.kafka = kafka;
    }

    public KafkaConfiguration kafka() {
        return kafka;
    }

}
