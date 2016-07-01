package com.bitdubai.sub_app.intra_user_identity.fragment_factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum IntraUserIdentityFragmentsEnumType implements FermatFragmentsEnumType<IntraUserIdentityFragmentsEnumType> {

    CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT("CCPSACCIMF"),
    CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT("CCPSACCICIF"),
    CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY("CCPSACCIGI")
    ;

    private String key;

    IntraUserIdentityFragmentsEnumType(String key) {
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

    public static IntraUserIdentityFragmentsEnumType getValue(String name) {
        for (IntraUserIdentityFragmentsEnumType fragments : IntraUserIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
