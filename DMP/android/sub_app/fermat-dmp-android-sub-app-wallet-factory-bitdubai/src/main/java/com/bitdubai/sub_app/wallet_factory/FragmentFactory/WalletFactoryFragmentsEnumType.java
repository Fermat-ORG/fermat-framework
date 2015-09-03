package com.bitdubai.sub_app.wallet_factory.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum WalletFactoryFragmentsEnumType implements FermatFragmentsEnumType<WalletFactoryFragmentsEnumType> {


    CWP_WALLET_FACTORY_MANAGER_FRAGMENT("CWFMF"),
    CWP_WALLET_FACTORY_PROJECTS_FRAGMENT("CWFPF"),
    CWP_WALLET_FACTORY_SEND_FRAGMENT("CWFSF"),
    CWP_WALLET_FACTORY_MAIN_FRAGMENT("CWFMF"),
    CWP_WALLET_FACTORY_EDIT_MODE("CWFEM");


    private String key;

    WalletFactoryFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey()   { return this.key; }

    @Override
    public String toString(){
        return key;
    }


    public static WalletFactoryFragmentsEnumType getValue(String name) {
        for (WalletFactoryFragmentsEnumType fragments : WalletFactoryFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
