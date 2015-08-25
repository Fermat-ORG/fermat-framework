package com.bitdubai.sub_app.wallet_store.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.WalletFragments;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum Fragments implements WalletFragments {

    CWP_WALLET_STORE_MAIN_ACTIVITY("CWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWSMDA"),

    /**
     * Va a ser eliminado. No se usa.
     */
    @Deprecated
    CWP_WALLET_STORE_ACCEPTED_NEARBY_FRAGMENT("CWSANF"),

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
    CWP_WALLET_STORE_PAID_FRAGMENT("CWSPF"),
    /**
     * Va a ser eliminado. No se usa.
     */
    @Deprecated

    CWP_WALLET_STORE_SEARCH_MODE("CWSSM");


    private String key;

    Fragments(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }


    public String toString() {
        return key;
    }

    public static Fragments getValueFromString(String name) {
        for (Fragments fragments : Fragments.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
