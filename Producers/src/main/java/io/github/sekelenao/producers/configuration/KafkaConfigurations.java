package io.github.sekelenao.producers.configuration;

import io.github.sekelenao.smallyaml.api.document.property.MultipleMandatoryIdentifier;
import io.github.sekelenao.smallyaml.api.document.property.SingleMandatoryIdentifier;

public class KafkaConfigurations {

    public static final MultipleMandatoryIdentifier BOOTSTRAP_SERVERS = MultipleMandatoryIdentifier.define("kafka.bootstrap.servers");

    public static final SingleMandatoryIdentifier EVENTS_TOPIC = SingleMandatoryIdentifier.define("kafka.topic.events");

    private KafkaConfigurations(){
        throw new AssertionError("You cannot instantiate this class");
    }

}
