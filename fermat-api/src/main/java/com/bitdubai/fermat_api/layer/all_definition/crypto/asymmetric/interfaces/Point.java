package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces;

import java.math.BigInteger;

public interface Point {
    BigInteger getX();

    BigInteger getY();

    String toUncompressedString();

    String toCompressedString();

    boolean equals(Object obj);

    int hashCode();
}
