package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum Wallets {
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI("kids"),
    CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI("teens"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI("adults"),
    CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI("young"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI("basic"),
    CBP_CRYPTO_BROKER_WALLET("cbp_crypto_broker_wallet"),
    CBP_CRYPTO_CUSTOMER_WALLET("cbp_crypto_customer_wallet"),
    CSH_CASH_WALLET("csh_cash_wallet"),
    BNK_BANKING_WALLET("bnk_banking_wallet");

    private final String code;

    Wallets(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static Wallets getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "basic":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI;
            case "young":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI;
            case "adults":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI;
            case "teens":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI;
            case "kids":
                return Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI;
            case "cbp_crypto_broker_wallet":
                return CBP_CRYPTO_BROKER_WALLET;
            case "cbp_crypto_customer_wallet":
                return CBP_CRYPTO_CUSTOMER_WALLET;
<<<<<<< HEAD
            case "csh_cash_wallet":
                return CSH_CASH_WALLET;
=======
            case "bnk_banking_wallet":
                return BNK_BANKING_WALLET;
>>>>>>> upstream_nelson/develop
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Wallets enum");
        }

        /**
         * Return by default.
         */
        //return null;
    }
}

