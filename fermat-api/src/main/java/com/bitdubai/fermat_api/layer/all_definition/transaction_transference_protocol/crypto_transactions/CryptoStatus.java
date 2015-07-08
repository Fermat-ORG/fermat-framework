package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 11/06/15.
 */
public enum CryptoStatus {
    PENDING_SUBMIT ("PSB"),
    ON_CRYPTO_NETWORK ("OCN"),
    ON_BLOCKCHAIN ("OBC"),
    IRREVERSIBLE ("IRR"),
    REVERSED_ON_BLOCKCHAIN ("ROB"),
    REVERSED_ON_CRYPTO_NETWORK ("RON");

    private final String code;

    CryptoStatus(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static CryptoStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "PSB": return CryptoStatus.PENDING_SUBMIT;
            case "OCN": return CryptoStatus.ON_CRYPTO_NETWORK;
            case "OBC": return CryptoStatus.ON_BLOCKCHAIN;
            case "IRR": return CryptoStatus.IRREVERSIBLE;
            case "ROB": return CryptoStatus.REVERSED_ON_BLOCKCHAIN;
            case "RON": return CryptoStatus.REVERSED_ON_CRYPTO_NETWORK;
        }

        /**
         * If we try to cpmvert am invalid string.
         */
        throw new InvalidParameterException(code);
    }


}
