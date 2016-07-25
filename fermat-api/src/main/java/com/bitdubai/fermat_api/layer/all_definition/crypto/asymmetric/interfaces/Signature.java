package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces;

import java.math.BigInteger;

public interface Signature {
    String SIGNATURE_SEPARATOR = " ";
    int SIGNATURE_SEPARATOR_PARTS = 2;

    BigInteger getR();

    BigInteger getS();

    boolean verifyMessageSignature(final BigInteger messageHash, final PublicKey publicKey);
} 
