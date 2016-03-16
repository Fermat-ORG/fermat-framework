package com.bitdubai.reference_wallet.fan_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Miguel Payarez on 15/03/16.
 */
public enum FanWalletEnumtype implements FermatFragmentsEnumType <FanWalletEnumtype>,ResourceProviderManager {


    TKY_FAN_WALLET_SONGS_TAB_FRAGMENT("TKYWSTF"),
    TKY_FAN_WALLET_FOLLOWING_TAB_FRAGMENT("TKYWFTF");


    private String key;

    FanWalletEnumtype(String key){
        this.key=key;
    }

    public static FanWalletEnumtype getValues(String name){
        for(FanWalletEnumtype fragments: FanWalletEnumtype.values()){
            if(fragments.getKey().equals(name)){
                return fragments;
            }

        }

        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
