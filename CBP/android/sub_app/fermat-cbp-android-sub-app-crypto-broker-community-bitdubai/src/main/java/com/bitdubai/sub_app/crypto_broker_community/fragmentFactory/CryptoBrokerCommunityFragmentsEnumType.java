package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoBrokerCommunityFragmentsEnumType implements FermatFragmentsEnumType<CryptoBrokerCommunityFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    CryptoBrokerCommunityFragmentsEnumType(String key) {
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

    public static CryptoBrokerCommunityFragmentsEnumType getValue(String name) {
        for (CryptoBrokerCommunityFragmentsEnumType fragments : CryptoBrokerCommunityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
