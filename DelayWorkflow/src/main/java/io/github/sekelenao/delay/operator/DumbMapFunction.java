package io.github.sekelenao.delay.operator;

import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Objects;

public class DumbMapFunction implements MapFunction<TrainEvent, TrainEvent> {

    private final String jobName;

    public DumbMapFunction(String jobName) {
        this.jobName = Objects.requireNonNull(jobName, "jobName must not be null");
    }

    @Override
    public TrainEvent map(TrainEvent value) {
        return new TrainEvent(value.id(), jobName, value.timestamp());
    }

}
