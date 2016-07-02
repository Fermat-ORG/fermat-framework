package com.bitdubai.sub_app.art_fan_identity.fragment_factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public enum ArtFanUserIdentityFragmentsEnumType implements FermatFragmentsEnumType<ArtFanUserIdentityFragmentsEnumType> {
    ART_SUB_APP_FAN_IDENTITY_TEST_FRAGMENT("ARTSAFITF"),
    ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT("ARTSAFICIF")
            ;
    private String key;

    ArtFanUserIdentityFragmentsEnumType(String key) {
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

    public static ArtFanUserIdentityFragmentsEnumType getValue(String name) {
        for (ArtFanUserIdentityFragmentsEnumType fragments : ArtFanUserIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}

