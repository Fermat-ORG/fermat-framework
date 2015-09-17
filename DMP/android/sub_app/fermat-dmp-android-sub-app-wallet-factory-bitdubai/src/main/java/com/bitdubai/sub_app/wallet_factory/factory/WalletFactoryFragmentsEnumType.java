package com.bitdubai.sub_app.wallet_factory.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * WalletFactoryFragmentsEnumType
 *
 * @author Matias Furszyfer on 2015.07.22..
 * @author Francisco Vásquez
 */

public enum WalletFactoryFragmentsEnumType implements FermatFragmentsEnumType<WalletFactoryFragmentsEnumType> {


    CWP_WALLET_FACTORY_DEVELOPER_PROJECTS("CWFDP"),
    CWP_WALLET_FACTORY_AVAILABLE_PROJECTS("CWFAP");


    private String key;

    WalletFactoryFragmentsEnumType(String key) {
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


    public static WalletFactoryFragmentsEnumType getValue(String name) {
        for (WalletFactoryFragmentsEnumType fragments : WalletFactoryFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
