package io.github.sekelenao.delay.operator;

import io.github.sekelenao.delay.configuration.JobConfiguration;
import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.api.common.functions.MapFunction;

public class DumbMapFunction implements MapFunction<TrainEvent, TrainEvent> {

    private final String jobName;

    public DumbMapFunction(JobConfiguration jobConfiguration){
        this.jobName = jobConfiguration.jobName();
    }

    @Override
    public TrainEvent map(TrainEvent value) {
        return new TrainEvent(value.id(), jobName, value.timestamp());
    }

}
