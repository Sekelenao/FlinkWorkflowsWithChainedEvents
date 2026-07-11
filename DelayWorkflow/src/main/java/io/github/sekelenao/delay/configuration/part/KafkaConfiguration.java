package io.github.sekelenao.delay.configuration.part;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class KafkaConfiguration {

    @NotEmpty
    private final List<String> bootstrapServers;

    @NotBlank
    private final String topic;

    @NotBlank
    private final String groupId;

    @JsonCreator
    public KafkaConfiguration(
        @JsonProperty("bootstrap-servers") List<String> bootstrapServers,
        @JsonProperty("topic") String topic,
        @JsonProperty("group-id") String groupId
    ) {
        this.bootstrapServers = bootstrapServers;
        this.topic = topic;
        this.groupId = groupId;
    }

    public List<String> bootstrapServers() {
        return bootstrapServers;
    }

    public String topic() {
        return topic;
    }

    public String groupId() {
        return groupId;
    }
}
