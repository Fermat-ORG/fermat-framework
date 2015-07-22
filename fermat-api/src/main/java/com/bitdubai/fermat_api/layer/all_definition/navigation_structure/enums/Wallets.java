package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

/**
 * Created by rodrigo on 2015.07.20..
 */
public enum Wallets {
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI("CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI"),
    CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI("CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI("CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI"),
    CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI("CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI("CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI");

    private String key;

    Wallets(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }



    public String toString(){
        return key;
    }

    public static Wallets getValueFromString(String name) {
        for (Wallets wallet : Wallets.values()) {
            if (wallet.key.equals(name)) {
                return wallet;
            }
        }
        // throw an IllegalArgumentException or return null
        //throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
