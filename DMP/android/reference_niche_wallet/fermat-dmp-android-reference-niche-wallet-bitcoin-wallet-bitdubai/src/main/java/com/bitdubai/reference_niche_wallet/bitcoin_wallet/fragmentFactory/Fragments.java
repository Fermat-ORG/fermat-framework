package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.WalletFragments;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum Fragments implements WalletFragments {


    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND("BitcoinSendFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE("BitcoinBalanceFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE("BitcoinReceiveFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS("BitcoinTransactionFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS("BitcoinContactFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS("BitcoinCreateContactFragment");


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
