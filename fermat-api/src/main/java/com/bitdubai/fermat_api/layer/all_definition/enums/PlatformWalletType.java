package com.bitdubai.fermat_api.layer.all_definition.enums;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType</code>
 * enums the platform wallet types.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public enum PlatformWalletType {

    BASIC_WALLET_BITCOIN_WALLET ("BWBW"),
    BASIC_WALLET_DISCOUNT_WALLET ("BWDW"),
    BASIC_WALLET_FIAT_WALLET ("BWFW"),
    BASIC_WALLET_LOSS_PROTECTED_WALLET ("BWLP"),
    COMPOSITE_WALLET_MULTI_ACCOUNT("CWMA");


    private final String code;

    PlatformWalletType(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static PlatformWalletType getByCode(String code) {

        switch (code) {
            case "BWBW": return PlatformWalletType.BASIC_WALLET_BITCOIN_WALLET;
            case "BWDW": return PlatformWalletType.BASIC_WALLET_DISCOUNT_WALLET;
            case "BWFW": return PlatformWalletType.BASIC_WALLET_FIAT_WALLET;
            case "BWLP": return PlatformWalletType.BASIC_WALLET_LOSS_PROTECTED_WALLET;
            case "CWMA": return PlatformWalletType.COMPOSITE_WALLET_MULTI_ACCOUNT;
        }

        /**
         * Return by default.
         */
        return PlatformWalletType.BASIC_WALLET_BITCOIN_WALLET;
    }
    
}
