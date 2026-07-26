package io.github.sekelenao.producers.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class TrainEvent implements Serializable {

    private UUID id;
    private String station;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant timestamp;

    public TrainEvent() {
    }

    @JsonCreator
    public TrainEvent(
        @JsonProperty("id") UUID id,
        @JsonProperty("station") String station,
        @JsonProperty("timestamp") Instant timestamp
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.station = Objects.requireNonNull(station, "station must not be null");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp must not be null");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public UUID id() {
        return id;
    }

    public String station() {
        return station;
    }

    public Instant timestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainEvent that = (TrainEvent) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(station, that.station) &&
               Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, station, timestamp);
    }

    @Override
    public String toString() {
        return "TrainEvent{" +
               "id=" + id +
               ", station='" + station + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}
