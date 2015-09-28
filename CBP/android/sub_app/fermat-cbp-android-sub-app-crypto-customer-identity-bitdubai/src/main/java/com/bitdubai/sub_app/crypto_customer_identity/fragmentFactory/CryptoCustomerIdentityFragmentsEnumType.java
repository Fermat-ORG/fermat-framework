package com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoCustomerIdentityFragmentsEnumType implements FermatFragmentsEnumType<CryptoCustomerIdentityFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    CryptoCustomerIdentityFragmentsEnumType(String key) {
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

    public static CryptoCustomerIdentityFragmentsEnumType getValue(String name) {
        for (CryptoCustomerIdentityFragmentsEnumType fragments : CryptoCustomerIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
