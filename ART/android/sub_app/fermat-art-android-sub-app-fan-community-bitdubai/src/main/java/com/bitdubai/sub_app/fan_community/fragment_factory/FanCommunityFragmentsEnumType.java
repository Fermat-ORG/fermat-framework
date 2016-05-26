package com.bitdubai.sub_app.fan_community.fragment_factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public enum FanCommunityFragmentsEnumType implements
        FermatFragmentsEnumType<
                FanCommunityFragmentsEnumType> {

    //ART_WALLET_STORE_ALL_FRAGMENT("AWSAF"),

    ART_SUB_APP_FAN_COMMUNITY_CONNECTIONS("ARTSAFCC"),
    ART_SUB_APP_FAN_COMMUNITY_CONNECTION_DETAIL("ARTSAFCCD"),
    ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS("ASAFCCN"),
    ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE("ASAFCCOP"),
    ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD("ASAFCCW"),
    ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST("ASAFCCFL"),
    ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST("ASAFCLIL"),
            ;

    private String key;

    FanCommunityFragmentsEnumType(String key) {
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

    public static FanCommunityFragmentsEnumType getValue(String name) {
        for (FanCommunityFragmentsEnumType fragments : FanCommunityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
