package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.WalletFragments;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum Fragments implements WalletFragments {


    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND("CWRWBTCABS"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE("CWRWBTCABB"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE("CWRWBTCABR"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS("CWRWBTCABT"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS("CWRWBTCABC"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS("BitcoinCreateContactFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST("CWRWBTCABMR"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_BOOK("CWRWBTCABTB"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE("CWRWBTCABTA")
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
