package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 11/06/15.
 */
public enum CryptoStatus {
    IDENTIFIED ("IDF"),
    RECEIVED ("RCV"),
    CONFIRMED ("CFM"),
    REVERSED ("RVS");

    private final String code;

    CryptoStatus(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static CryptoStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "IDF": return CryptoStatus.IDENTIFIED;
            case "RCV": return CryptoStatus.RECEIVED;
            case "CFM": return CryptoStatus.CONFIRMED;
            case "RVS": return CryptoStatus.REVERSED;
        }

        /**
         * If we try to cpmvert am invalid string.
         */
        throw new InvalidParameterException(code);
    }


}
