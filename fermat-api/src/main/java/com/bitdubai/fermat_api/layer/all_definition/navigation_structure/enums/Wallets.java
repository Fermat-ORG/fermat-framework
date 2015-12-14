package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum Wallets {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    BNK_BANKING_WALLET("BNKBW"),
    CBP_CRYPTO_BROKER_WALLET("CBPCBW"),
    CBP_CRYPTO_CUSTOMER_WALLET("CBPCCW"),
    CSH_CASH_WALLET("CSHCW"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI("ADULTS"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI("KIDS"),
    CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI("TEENS"),
    CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI("YOUNG"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI("BASIC"),
    DAP_ASSET_ISSUER_WALLET("DAPAIW");

    private final String code;

    Wallets(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static Wallets getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BASIC":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI;
            case "YOUNG":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI;
            case "ADULTS":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI;
            case "TEENS":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI;
            case "KIDS":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI;
            case "CBPCBW":
                return CBP_CRYPTO_BROKER_WALLET;
            case "CBPCCW":
                return CBP_CRYPTO_CUSTOMER_WALLET;
            case "CSHCW":
                return CSH_CASH_WALLET;
            case "BNKBW":
                return BNK_BANKING_WALLET;
            case "DAPAIW":
                return DAP_ASSET_ISSUER_WALLET;
            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This Code Is Not Valid for the Wallets enum");
        }

        /**
         * Return by default.
         */
        //return null;
    }
}

