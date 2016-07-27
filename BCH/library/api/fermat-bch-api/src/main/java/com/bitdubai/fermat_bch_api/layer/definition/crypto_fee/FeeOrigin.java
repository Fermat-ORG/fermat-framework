package com.bitdubai.fermat_bch_api.layer.definition.crypto_fee;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by rodrigo on 7/2/16.
 * Specifies from where the Fee will be taken. When sending crypto on a transaction, the fee can be deducted from the amount of crypto
 * to send or from the funds of the wallet. Example, If we are sending 10000 satoshis with a 5000 fee.
 * If fee comes from the amount, then we are sending a total of 10000 satoshis, 5000 to user and 5000 fee..
 * If the fee comes from the funds, then we are sending a total of 15000 satothis, 10000 to user and 5000 fee.
 */

public enum FeeOrigin implements Serializable {
    SUBSTRACT_FEE_FROM_AMOUNT("AMOUNT"),
    SUBSTRACT_FEE_FROM_FUNDS("FUNDS");

    private String code;

    FeeOrigin(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static FeeOrigin getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AMOUNT":
                return SUBSTRACT_FEE_FROM_AMOUNT;
            case "FUNDS":
                return SUBSTRACT_FEE_FROM_FUNDS;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the FeeOrigin enum.");
        }
    }
}
