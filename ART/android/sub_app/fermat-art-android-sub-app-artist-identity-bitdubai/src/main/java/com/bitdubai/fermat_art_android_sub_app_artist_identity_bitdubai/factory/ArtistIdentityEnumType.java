package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.factory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 10/03/16.
 */
public enum ArtistIdentityEnumType implements FermatFragmentsEnumType<ArtistIdentityEnumType> {


    ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE("ASAAICP")
    ;


    private String key;

    ArtistIdentityEnumType(String key) {
        this.key = key;
    }

    public static ArtistIdentityEnumType getValue(String name) {
        for (ArtistIdentityEnumType fragments : ArtistIdentityEnumType.values()) {
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
    @Override
    public String toString() {
        return key;
    }
}