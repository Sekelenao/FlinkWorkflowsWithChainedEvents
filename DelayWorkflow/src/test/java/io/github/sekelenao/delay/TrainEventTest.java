package io.github.sekelenao.delay;

import io.github.sekelenao.delay.event.TrainEvent;
import io.github.sekelenao.flinkboot.test.FlinkbootAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class TrainEventTest {

    @Test
    @DisplayName("Train event is POJO")
    void trainEventIsPojo() {
        FlinkbootAssertions.isPojo(TrainEvent.class);
    }

}
