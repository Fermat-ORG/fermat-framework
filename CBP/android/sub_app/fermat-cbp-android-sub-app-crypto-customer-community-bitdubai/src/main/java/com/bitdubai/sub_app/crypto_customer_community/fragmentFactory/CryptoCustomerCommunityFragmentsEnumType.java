package com.bitdubai.sub_app.crypto_customer_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoCustomerCommunityFragmentsEnumType implements FermatFragmentsEnumType<CryptoCustomerCommunityFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    CryptoCustomerCommunityFragmentsEnumType(String key) {
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

    public static CryptoCustomerCommunityFragmentsEnumType getValue(String name) {
        for (CryptoCustomerCommunityFragmentsEnumType fragments : CryptoCustomerCommunityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
