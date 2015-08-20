package com.bitdubai.sub_app.wallet_store.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.WalletFragments;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum Fragments implements WalletFragments {


    CWP_WALLET_STORE_ACCEPTED_NEARBY_FRAGMENT("CWSANF"),
    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),
    CWP_WALLET_STORE_FREE_FRAGMENT("CWSFF"),
    CWP_WALLET_STORE_PAID_FRAGMENT("CWSPF"),
    CWP_WALLET_STORE_SEARCH_MODE("CWSSM")
    ;


    private String key;

    Fragments(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }



    public String toString(){
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
