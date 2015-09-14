package com.bitdubai.sub_app.wallet_store.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum WalletStoreFragmentsEnumType implements FermatFragmentsEnumType<WalletStoreFragmentsEnumType> {

    CWP_WALLET_STORE_MAIN_ACTIVITY("CWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWSMDA"),

    /**
     * Va a ser eliminado. Usando momentaneamente para probar {@link com.bitdubai.sub_app.wallet_store.fragments.MainActivityFragment}
     */
    @Deprecated
    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),
    /**
     * Va a ser eliminado. Usando momentaneamente para probar {@link com.bitdubai.sub_app.wallet_store.fragments.DetailsActivityFragment}
     */
    @Deprecated
    CWP_WALLET_STORE_FREE_FRAGMENT("CWSFF"),
    /**
     * Va a ser eliminado. Usando momentaneamente para probar {@link com.bitdubai.sub_app.wallet_store.fragments.MoreDetailsActivityFragment}
     */
    @Deprecated
    CWP_WALLET_STORE_PAID_FRAGMENT("CWSPF")
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
        System.err.println("Method: getValue - TENGO RETURN NULL");
        return null;
    }
}
