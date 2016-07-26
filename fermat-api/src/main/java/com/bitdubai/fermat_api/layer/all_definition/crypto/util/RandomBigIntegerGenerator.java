package com.bitdubai.fermat_api.layer.all_definition.crypto.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomBigIntegerGenerator {


    public static final String MIN_BIGINTEGER_ADDRESS = "10000000000000000000000000000000000000000000000000000000000000000000000000000";
    public static final String MAX_BIGINTEGER_ADDRESS = "99999999999999999999999999999999999999999999999999999999999999999999999999999";

    private static final int MAX_ITERATIONS = 1000;

    private SecureRandom randomizer;

    public RandomBigIntegerGenerator() {
        randomizer = new SecureRandom();
    }

    public RandomBigIntegerGenerator(SecureRandom randomizer) {
        this.randomizer = randomizer;
    }

    public BigInteger generateRandom() {
        return createRandomBigIntegerInRange(new BigInteger(MIN_BIGINTEGER_ADDRESS), new BigInteger(MAX_BIGINTEGER_ADDRESS), randomizer);
    }

    /*
        random biginteger generator from https://github.com/bitcoin-labs/bitcoinj-minimal/blob/master/bouncycastle/util/BigIntegers.java
     */
    private BigInteger createRandomBigIntegerInRange(final BigInteger min, final BigInteger max, final SecureRandom random) {
        int comparison = min.compareTo(max);
        if (comparison >= 0) {
            if (comparison > 0)
                throw new IllegalArgumentException("'min' may not be greater than 'max'");
            return min;
        }

        if (min.bitLength() > max.bitLength() / 2)
            return createRandomBigIntegerInRange(BigInteger.ZERO, max.subtract(min), random).add(min);

        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            BigInteger x = new BigInteger(max.bitLength(), randomizer);
            if (x.compareTo(min) >= 0 && x.compareTo(max) <= 0)
                return x;
        }
        return new BigInteger(max.subtract(min).bitLength() - 1, randomizer).add(min);
    }

}
