package com.bitdubai.reference_wallet.fan_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Miguel Payarez on 15/03/16.
 */
public enum FanWalletEnumType implements FermatFragmentsEnumType <FanWalletEnumType> {

    TKY_FAN_WALLET_MAIN_ACTIVITY("TKYFWMA"),
    TKY_FAN_WALLET_SONGS_TAB_FRAGMENT("TKYFWSTF"),
    TKY_FAN_WALLET_FOLLOWING_TAB_FRAGMENT("TKYFWFTF");


    private String key;

    FanWalletEnumType(String key){
        this.key=key;
    }

    public static FanWalletEnumType getValues(String name){
        for(FanWalletEnumType fragments: FanWalletEnumType.values()){
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
