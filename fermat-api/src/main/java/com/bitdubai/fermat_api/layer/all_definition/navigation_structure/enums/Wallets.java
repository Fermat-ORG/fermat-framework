package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum Wallets {
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI("kids"),
    CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI("teens"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI("adults"),
    CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI("young"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI("basic");

    private final String code;

    Wallets(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static Wallets getByCode(String code) {

        switch (code) {
            case "basic": return Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI;
            case "young": return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI;
            case "adults": return Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI;
            case "teens": return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI;
            case "kids": return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI;
        }

        /**
         * Return by default.
         */
        return null;
    }
}
