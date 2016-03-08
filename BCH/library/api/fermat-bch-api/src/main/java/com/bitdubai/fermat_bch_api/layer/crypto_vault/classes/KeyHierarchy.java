package com.bitdubai.fermat_bch_api.layer.crypto_vault.classes;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;

/**
 * Created by rodrigo on 3/8/16.
 */
public abstract  class KeyHierarchy extends DeterministicHierarchy {

    /**
     * Super constructor
     * @param rootKey
     */
    public KeyHierarchy(DeterministicKey rootKey) {
        super(rootKey);
    }

    /**
     * gets the private key of the corresponding public Key
     * @param publicKey
     * @return
     */
    public ECKey getPrivateKey (byte[] publicKey){
        return null;
    }
}
