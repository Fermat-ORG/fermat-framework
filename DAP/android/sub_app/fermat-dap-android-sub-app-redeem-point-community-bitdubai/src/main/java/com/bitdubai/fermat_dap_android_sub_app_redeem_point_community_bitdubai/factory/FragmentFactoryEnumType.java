package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by francisco on 14/10/15.
 */
public enum FragmentFactoryEnumType implements FermatFragmentsEnumType<FragmentFactoryEnumType> {

    DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN("DAPARPCAM");

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
