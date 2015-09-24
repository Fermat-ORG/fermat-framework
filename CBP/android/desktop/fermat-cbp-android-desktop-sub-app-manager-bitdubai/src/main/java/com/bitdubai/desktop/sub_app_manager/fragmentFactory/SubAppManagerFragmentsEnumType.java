package com.bitdubai.desktop.sub_app_manager.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum SubAppManagerFragmentsEnumType implements FermatFragmentsEnumType<SubAppManagerFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    SubAppManagerFragmentsEnumType(String key) {
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

    public static SubAppManagerFragmentsEnumType getValue(String name) {
        for (SubAppManagerFragmentsEnumType fragments : SubAppManagerFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
