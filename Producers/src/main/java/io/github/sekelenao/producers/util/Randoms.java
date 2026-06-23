package io.github.sekelenao.producers.util;

import java.security.SecureRandom;

public final class Randoms {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static boolean percentage(int percentage){
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        return RANDOM.nextInt(100) < percentage;
    }

}
