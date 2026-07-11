package io.github.sekelenao.delay.kafka;

import io.github.sekelenao.delay.configuration.JobConfiguration;
import io.github.sekelenao.delay.event.TrainEvent;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

public class KafkaSourceFactory {

    private final String topic;

    private final String bootstrapServers;

    private final String consumerGroup;

    private final OffsetsInitializer startingOffset;

    public KafkaSourceFactory(JobConfiguration configuration){
        this.topic = configuration.kafka().topic();
        this.bootstrapServers = String.join(",", configuration.kafka().bootstrapServers());
        this.consumerGroup = configuration.kafka().groupId();
        this.startingOffset = OffsetsInitializer.earliest();
    }

    public KafkaSource<TrainEvent> supply(){
        return KafkaSource.<TrainEvent>builder()
            .setTopics(topic)
            .setBootstrapServers(bootstrapServers)
            .setGroupId(consumerGroup)
            .setStartingOffsets(startingOffset)
            .setDeserializer(new TrainEventKafkaDeserializer())
            .build();
    }

}
