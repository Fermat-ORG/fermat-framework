package org.fermat.fermat_dap_android_sub_app_asset_user_community.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by francisco on 14/10/15.
 */
public enum FragmentFactoryEnumType implements FermatFragmentsEnumType<FragmentFactoryEnumType> {

    DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN("DAPAUCAM"),
    DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT("DAPAUCAGF"),
    DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT("DAUCAAGUF"),
    DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT("DAUCAPF"),
    DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT("DAUCCLOPF"),
    DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS("DAUCAAU"),
    DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT("DAUCCLF"),
    DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT("DAUCNF"),
//    DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT("DAUCSF"),

    ;

    private String key;

    FragmentFactoryEnumType(String key) {
        this.key = key;
    }

    public static FragmentFactoryEnumType getValue(String name) {
        for (FragmentFactoryEnumType fragments : FragmentFactoryEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}