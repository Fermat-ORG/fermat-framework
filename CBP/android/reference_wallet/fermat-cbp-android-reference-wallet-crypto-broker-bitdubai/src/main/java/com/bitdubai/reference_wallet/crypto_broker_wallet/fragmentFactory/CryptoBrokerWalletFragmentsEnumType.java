package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoBrokerWalletFragmentsEnumType implements FermatFragmentsEnumType<CryptoBrokerWalletFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    CryptoBrokerWalletFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public String toString() {
        return key;
    }

    public static CryptoBrokerWalletFragmentsEnumType getValue(String name) {
        for (CryptoBrokerWalletFragmentsEnumType fragments : CryptoBrokerWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
