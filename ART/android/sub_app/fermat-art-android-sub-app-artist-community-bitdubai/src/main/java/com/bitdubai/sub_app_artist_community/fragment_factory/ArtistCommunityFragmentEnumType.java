package com.bitdubai.sub_app_artist_community.fragment_factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public enum ArtistCommunityFragmentEnumType implements FermatFragmentsEnumType<ArtistCommunityFragmentEnumType> {
    //ART_ARTIST_WALLET_STORE_ALL_FRAGMENT("AAWSAF"),

    ART_SUB_APP_ARTIST_COMMUNITY_CONNECTIONS("ARTSAACC"),
    ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_DETAIL("ARTSAACCD"),
    ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS("ASAACCN"),
    ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE("ASAACCOP"),
    ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD("ASAACCW"),
    ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST("ASAACCFL"),
    ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST("ASAACLIL"),
    ;

    private String key;

    ArtistCommunityFragmentEnumType(String key) {
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

    public static ArtistCommunityFragmentEnumType getValue(String name) {
        for (ArtistCommunityFragmentEnumType fragments : ArtistCommunityFragmentEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
