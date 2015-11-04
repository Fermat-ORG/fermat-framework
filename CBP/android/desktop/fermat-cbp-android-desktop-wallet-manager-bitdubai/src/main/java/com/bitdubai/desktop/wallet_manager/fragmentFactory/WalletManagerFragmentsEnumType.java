package com.bitdubai.desktop.wallet_manager.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum WalletManagerFragmentsEnumType implements FermatFragmentsEnumType<WalletManagerFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    WalletManagerFragmentsEnumType(String key) {
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

    public static WalletManagerFragmentsEnumType getValue(String name) {
        for (WalletManagerFragmentsEnumType fragments : WalletManagerFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
