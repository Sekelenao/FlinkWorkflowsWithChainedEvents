package io.github.sekelenao.delay.operator;

import io.github.sekelenao.delay.configuration.JobConfiguration;
import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.api.common.functions.MapFunction;

public class DumbMapFunction implements MapFunction<TrainEvent, TrainEvent> {

    private final JobConfiguration jobConfiguration;

    public DumbMapFunction(JobConfiguration jobConfiguration){
        this.jobConfiguration = jobConfiguration;
    }

    @Override
    public TrainEvent map(TrainEvent value) {
        return new TrainEvent(value.id(), jobConfiguration.jobName(), value.timestamp());
    }

}
