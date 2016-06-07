package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 21/03/16.
 */

public enum TkyArtistIdentityEnumType implements FermatFragmentsEnumType<TkyArtistIdentityEnumType> {

    ART_SUB_APP_FAN_IDENTITY_TEST_FRAGMENT("ARTSAFITF"),
    TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE("TACACP"),
    ;


    private String key;

    TkyArtistIdentityEnumType(String key) {
        this.key = key;
    }

    public static TkyArtistIdentityEnumType getValue(String name) {
        for (TkyArtistIdentityEnumType fragments : TkyArtistIdentityEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }


    @Override
    public String getKey() {
        return this.key;
    }
}