package io.github.sekelenao.producers.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record TrainEvent(
    UUID id,
    String station,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    Instant timestamp
) {

    public TrainEvent {
        Objects.requireNonNull(id);
        Objects.requireNonNull(station);
        Objects.requireNonNull(timestamp);
    }

}
