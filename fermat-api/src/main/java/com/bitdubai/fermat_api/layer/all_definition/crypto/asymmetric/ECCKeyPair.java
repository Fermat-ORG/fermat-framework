package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;

import java.io.Serializable;


public class ECCKeyPair implements KeyPair, Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private final String privateKey;
    private final String publicKey;

    public ECCKeyPair(final String privateKey, String publicKey) throws IllegalArgumentException {
        if (publicKey == null || publicKey.isEmpty())
            throw new IllegalArgumentException();
        if (!publicKey.equals(AsymmetricCryptography.derivePublicKey(privateKey)))
            throw new IllegalArgumentException();
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public ECCKeyPair(final String privateKey) throws IllegalArgumentException {
        this(privateKey, AsymmetricCryptography.derivePublicKey(privateKey));
    }

    public ECCKeyPair() {
        this(AsymmetricCryptography.createPrivateKey());
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ECCKeyPair))
            return false;
        ECCKeyPair compare = (ECCKeyPair) o;
        return privateKey.equals(compare.getPrivateKey());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += privateKey.hashCode();
        c += publicKey.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

}
