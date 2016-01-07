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
public class ExtendedPublicKey extends DeterministicKey {
    public ExtendedPublicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, LazyECPoint publicAsPoint, BigInteger priv, DeterministicKey parent) {
        super(childNumberPath, chainCode, publicAsPoint, priv, parent);
    }

    public ExtendedPublicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, ECPoint publicAsPoint, BigInteger priv, DeterministicKey parent) {
        super(childNumberPath, chainCode, publicAsPoint, priv, parent);
    }

    public ExtendedPublicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, BigInteger priv, DeterministicKey parent) {
        super(childNumberPath, chainCode, priv, parent);
    }

    public ExtendedPublicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, KeyCrypter crypter, LazyECPoint pub, EncryptedData priv, DeterministicKey parent) {
        super(childNumberPath, chainCode, crypter, pub, priv, parent);
    }

    public ExtendedPublicKey(DeterministicKey keyToClone, DeterministicKey newParent) {
        super(keyToClone, newParent);
    }
}
