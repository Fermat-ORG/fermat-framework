package com.bitdubai.fermat_bch_api.layer.crypto_network.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 10/12/15.
 */
public enum TransactionTypes {
    INCOMING("INC"),
    OUTGOING("OUT");


    private String code;

    TransactionTypes(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static TransactionTypes getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "INC":
                return INCOMING;
            case "OUT":
                return OUTGOING;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoVaults enum.");
        }
    }
}
