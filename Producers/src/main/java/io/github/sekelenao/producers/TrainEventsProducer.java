package io.github.sekelenao.producers;

import io.github.sekelenao.producers.configuration.TrainConfigurations;
import io.github.sekelenao.producers.generator.TrainEventTimelineGenerator;
import io.github.sekelenao.producers.kafka.KafkaUtilities;
import io.github.sekelenao.smallyaml.api.document.BoundedDocument;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class TrainEventsProducer {

    private static final String CONFIGURATION_RESOURCE_LOCATION = "/train.yaml";

    private static BoundedDocument loadConfigurationFromResources() throws IOException {
        try(var inputStream = TrainEventsProducer.class.getResourceAsStream(CONFIGURATION_RESOURCE_LOCATION)){
            Objects.requireNonNull(inputStream, "Configuration file not found");
            return BoundedDocument.factoryBuilder()
                .scan(TrainConfigurations.class)
                .buildFactory()
                .createDocument(inputStream);
        }
    }

    public static void main(String[] args) throws IOException {
        var kafkaUtilities = KafkaUtilities.initialize();
        var configuration = loadConfigurationFromResources();
        var producerDuration = configuration.get(TrainConfigurations.PRODUCER_DURATION, Duration::parse);
        var timeToStop = Instant.now().plus(producerDuration);
        try (var producer = kafkaUtilities.createProducer()) {
            var trainEventTimelineGenerator = new TrainEventTimelineGenerator(configuration);
            while (Instant.now().isBefore(timeToStop)) {
                var eventTimeline = trainEventTimelineGenerator.generate();
                eventTimeline.stream()
                    .map(kafkaUtilities::createRecord)
                    .forEach(producer::send);
            }
        }
    }

}
