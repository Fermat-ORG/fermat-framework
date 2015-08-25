package com.bitdubai.sub_app.wallet_store.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum WalletStoreFragmentsEnumType implements FermatFragmentsEnumType<WalletStoreFragmentsEnumType> {


    CWP_WALLET_STORE_ACCEPTED_NEARBY_FRAGMENT("CWSANF"),
    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),
    CWP_WALLET_STORE_FREE_FRAGMENT("CWSFF"),
    CWP_WALLET_STORE_PAID_FRAGMENT("CWSPF"),
    CWP_WALLET_STORE_SEARCH_MODE("CWSSM")
    ;



    private String key;

    WalletStoreFragmentsEnumType(String key) {
        this.key = key;
    }
    WalletStoreFragmentsEnumType(){
}

    @Override
    public String getKey()   { return this.key; }


    @Override
    public String toString(){
        return key;
    }

    public static WalletStoreFragmentsEnumType getValue(String name) {
        for (WalletStoreFragmentsEnumType fragments : WalletStoreFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
