package com.bitdubai.sub_app.wallet_factory.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.WalletFragments;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum Fragments implements WalletFragments {


    CWP_WALLET_FACTORY_MANAGER_FRAGMENT("CWFMF"),
    CWP_WALLET_FACTORY_PROJECTS_FRAGMENT("CWFPF"),
    CWP_WALLET_FACTORY_SEND_FRAGMENT("CWFSF"),
    CWP_WALLET_FACTORY_MAIN_FRAGMENT("CWFMF"),
    CWP_WALLET_FACTORY_EDIT_MODE("CWFEM")
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
