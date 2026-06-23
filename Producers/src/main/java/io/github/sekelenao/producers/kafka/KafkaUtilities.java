package io.github.sekelenao.producers.kafka;

import io.github.sekelenao.producers.configuration.KafkaConfigurations;
import io.github.sekelenao.producers.serde.JsonSerializer;
import io.github.sekelenao.smallyaml.api.document.BoundedDocument;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public final class KafkaUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUtilities.class);

    private static final String RESOURCE_LOCATION = "/kafka.yaml";

    private final String topic;

    private final Properties properties;

    private KafkaUtilities(String topic, Properties properties){
        this.topic = Objects.requireNonNull(topic);
        this.properties = Objects.requireNonNull(properties);
    }

    public static KafkaUtilities initialize() throws IOException {
        LOGGER.info("Loading following configuration from resources: {}", RESOURCE_LOCATION);
        try(var inputStream = KafkaUtilities.class.getResourceAsStream(RESOURCE_LOCATION)){
            Objects.requireNonNull(inputStream, "Configuration file not found");
            var configuration = BoundedDocument.factoryBuilder()
                .scan(KafkaConfigurations.class)
                .buildFactory()
                .createDocument(inputStream);
            var properties = new Properties();
            var servers = configuration.get(KafkaConfigurations.BOOTSTRAP_SERVERS);
            LOGGER.info("Loaded following bootstrap servers: {}", servers);
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", servers));
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            var topic = configuration.get(KafkaConfigurations.EVENTS_TOPIC);
            LOGGER.info("Loaded following topic: {}", topic);
            return new KafkaUtilities(topic, properties);
        }
    }

    public KafkaProducer<String, Object> createProducer() {
        return new KafkaProducer<>(properties);
    }

    public ProducerRecord<String, Object> createRecord(Object value) {
        return new ProducerRecord<>(topic, Objects.requireNonNull(value));
    }

}
