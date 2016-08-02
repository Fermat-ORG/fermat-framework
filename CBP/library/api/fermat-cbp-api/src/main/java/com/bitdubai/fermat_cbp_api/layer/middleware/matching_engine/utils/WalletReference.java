package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils;

import java.io.Serializable;


/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference</code>
 * contains the information needed to identify a wallet.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public final class WalletReference implements Serializable {

    private final String publicKey;

    public WalletReference(final String publicKey) {

        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return "WalletReference{" +
                "publicKey='" + publicKey + '\'' +
                '}';
    }

}
