package io.github.sekelenao.producers;

import io.github.sekelenao.producers.configuration.TrainConfigurations;
import io.github.sekelenao.producers.generator.TrainEventTimelineGenerator;
import io.github.sekelenao.producers.kafka.KafkaUtilities;
import io.github.sekelenao.smallyaml.api.document.BoundedDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class TrainEventsProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainEventsProducer.class);

    private static final String CONFIGURATION_RESOURCE_LOCATION = "/train.yaml";

    private static BoundedDocument loadConfigurationFromResources() throws IOException {
        LOGGER.info("Loading following configuration from resources: {}", CONFIGURATION_RESOURCE_LOCATION);
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
        LOGGER.info("Producer will run for {} and stop at {}", producerDuration, timeToStop);
        try (var producer = kafkaUtilities.createProducer()) {
            var trainEventTimelineGenerator = new TrainEventTimelineGenerator(configuration);
            LOGGER.info("Currently producing...");
            while (Instant.now().isBefore(timeToStop)) {
                var eventTimeline = trainEventTimelineGenerator.generate();
                eventTimeline.stream()
                    .map(kafkaUtilities::createRecord)
                    .forEach(producer::send);
            }
            LOGGER.info("Successfully produced all events");
        }
    }

}
