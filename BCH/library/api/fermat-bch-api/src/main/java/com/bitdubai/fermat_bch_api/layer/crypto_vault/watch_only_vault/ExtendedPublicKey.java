package com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault;

import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.EncryptedData;
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.crypto.LazyECPoint;
import org.spongycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * Created by rodrigo on 12/30/15.
 */
public class ExtendedPublicKey {
    String actorPublicKey;
    byte[] pubKeyBytes;
    byte[] chainCode;

    public ExtendedPublicKey(String actorPublicKey, byte[] pubKeyBytes, byte[] chainCode) {
        this.actorPublicKey = actorPublicKey;
        this.pubKeyBytes = pubKeyBytes;
        this.chainCode = chainCode;
    }

    public String getActorPublicKey() {
        return actorPublicKey;
    }

    public byte[] getPubKeyBytes() {
        return pubKeyBytes;
    }

    public byte[] getChainCode() {
        return chainCode;
    }
}
