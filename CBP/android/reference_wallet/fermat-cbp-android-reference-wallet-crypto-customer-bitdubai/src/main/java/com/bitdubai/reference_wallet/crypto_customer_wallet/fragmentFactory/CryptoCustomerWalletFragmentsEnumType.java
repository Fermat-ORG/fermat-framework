package com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoCustomerWalletFragmentsEnumType implements FermatFragmentsEnumType<CryptoCustomerWalletFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    CryptoCustomerWalletFragmentsEnumType(String key) {
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

    public static CryptoCustomerWalletFragmentsEnumType getValue(String name) {
        for (CryptoCustomerWalletFragmentsEnumType fragments : CryptoCustomerWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
