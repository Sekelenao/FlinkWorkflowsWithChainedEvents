package io.github.sekelenao.producers.configuration;

import io.github.sekelenao.smallyaml.api.document.property.MultipleMandatoryIdentifier;
import io.github.sekelenao.smallyaml.api.document.property.SingleMandatoryIdentifier;

public final class TrainConfigurations {

    public static final SingleMandatoryIdentifier PRODUCER_DURATION = SingleMandatoryIdentifier.define("train.producer.duration");

    public static final MultipleMandatoryIdentifier STATIONS = MultipleMandatoryIdentifier.define("train.stations");

    public static final SingleMandatoryIdentifier REGULAR_DELAY_MS = SingleMandatoryIdentifier.define("train.regular-delay-in-ms");

    public static final SingleMandatoryIdentifier CHANCE_OF_HUGE_DELAY = SingleMandatoryIdentifier.define("train.chance-of-huge-delay");

    public static final SingleMandatoryIdentifier HUGE_DELAY_MS = SingleMandatoryIdentifier.define("train.huge-delay-in-ms");

    private TrainConfigurations(){
        throw new AssertionError("You cannot instantiate this class");
    }

}
