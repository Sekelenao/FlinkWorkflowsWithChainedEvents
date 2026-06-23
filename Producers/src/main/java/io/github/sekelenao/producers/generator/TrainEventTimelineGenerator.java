package io.github.sekelenao.producers.generator;

import io.github.sekelenao.producers.configuration.TrainConfigurations;
import io.github.sekelenao.producers.event.TrainEvent;
import io.github.sekelenao.producers.util.Randoms;
import io.github.sekelenao.smallyaml.api.document.BoundedDocument;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TrainEventTimelineGenerator {

    private final List<String> stations;

    private final InstantGenerator instantGenerator;

    public TrainEventTimelineGenerator(BoundedDocument configuration) {
        Objects.requireNonNull(configuration);
        this.stations = configuration.get(TrainConfigurations.STATIONS);
        var regularDelay = configuration.getLong(TrainConfigurations.REGULAR_DELAY_MS);
        var probabilityToHugeDelay = configuration.getInt(TrainConfigurations.CHANCE_OF_HUGE_DELAY);
        var hugeDelay = configuration.getLong(TrainConfigurations.HUGE_DELAY_MS);
        this.instantGenerator = new InstantGenerator(regularDelay, probabilityToHugeDelay, hugeDelay);
    }

    public List<TrainEvent> generate(){
        var trainId = UUID.randomUUID();
        return stations.stream()
            .filter(station -> Randoms.percentage(50))
            .map(station -> new TrainEvent(trainId, station, instantGenerator.generate()))
            .toList();
    }

}
