package io.github.sekelenao.producers.generator;

import io.github.sekelenao.producers.util.Randoms;

import java.time.Instant;

public final class InstantGenerator {

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
