package com.bitdubai.sup_app.tokenly_fan_user_identity.fragment_factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 22/03/16.
 */
public enum TokenlyFanUserIdentityFragmentsEnumType implements FermatFragmentsEnumType<TokenlyFanUserIdentityFragmentsEnumType> {

    TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT("TKYSUBAPPFANCREATE")
    ;
    private String key;

    TokenlyFanUserIdentityFragmentsEnumType(String key) {
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

    public static TokenlyFanUserIdentityFragmentsEnumType getValue(String name) {
        for (TokenlyFanUserIdentityFragmentsEnumType fragments : TokenlyFanUserIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
