package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Point;

import java.math.BigInteger;

public class EllipticCurvePoint implements Point {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1019;
    private static final int HASH_PRIME_NUMBER_ADD = 6299;

    private final BigInteger x;
    private final BigInteger y;

    public EllipticCurvePoint() {
        this.x = BigInteger.ONE;
        this.y = BigInteger.ONE;
    }

    public EllipticCurvePoint(final BigInteger x, final BigInteger y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public BigInteger getX() {
        return x;
    }

    @Override
    public BigInteger getY() {
        return y;
    }

    @Override
    public String toUncompressedString() {
        return new StringBuilder().append("04").append(String.format("%064X", x)).append(String.format("%064X", y)).toString();
    }

    @Override
    public String toCompressedString() {
        String prefix = "";
        if (y.mod(new BigInteger("2")).equals(BigInteger.ZERO))
            prefix = "02";
        else
            prefix = "03";
        return prefix + String.format("%064x", x);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point compare = (Point) o;
            return x.equals(compare.getX()) && y.equals(compare.getY());
        }
        return true;
    }

    @Override
    public int hashCode() {
        int c = 0;
        if (x != null)
            c += x.hashCode();
        if (y != null)
            c += y.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
