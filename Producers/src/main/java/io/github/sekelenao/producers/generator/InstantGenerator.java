package io.github.sekelenao.producers.generator;

import io.github.sekelenao.producers.util.Randoms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public final class InstantGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstantGenerator.class);

    private final long regularDelay;

    private final int probabilityToHugeDelay;

    private final long hugeDelay;

    private Instant now = Instant.now();

    public InstantGenerator(long regularDelay, int probabilityToHugeDelay, long hugeDelay){
        if(probabilityToHugeDelay < 0 || probabilityToHugeDelay > 100) {
            throw new IllegalArgumentException("Probability to be delayed must be between 0 and 100");
        }
        this.regularDelay = regularDelay;
        this.probabilityToHugeDelay = probabilityToHugeDelay;
        this.hugeDelay = hugeDelay;
        LOGGER.info(
            "Configured InstantGenerator with regular delay of {}ms and {}% chance to be delayed for {}ms",
            regularDelay, probabilityToHugeDelay, hugeDelay
        );
    }

    public Instant generate(){
        if(Randoms.percentage(probabilityToHugeDelay)){
            now = now.plusMillis(hugeDelay);
            return now;
        }
        now = now.plusMillis(regularDelay);
        return now;
    }

}
