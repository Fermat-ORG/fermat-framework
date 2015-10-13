package com.bitdubai.sub_app.wallet_store.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum WalletStoreFragmentsEnumType implements FermatFragmentsEnumType<WalletStoreFragmentsEnumType> {

    CWP_WALLET_STORE_MAIN_ACTIVITY("CWPWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWPWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWPWSMDA")
    ;


    private String key;

    WalletStoreFragmentsEnumType(String key) {
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

    public static WalletStoreFragmentsEnumType getValue(String name) {
        for (WalletStoreFragmentsEnumType fragments : WalletStoreFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
