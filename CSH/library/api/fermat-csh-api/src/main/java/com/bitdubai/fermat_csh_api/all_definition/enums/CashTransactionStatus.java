package com.bitdubai.fermat_csh_api.all_definition.enums;

import com.bitdubai.fermat_csh_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 23.09.15.
 * Modified by Alejandro Bicelis on 11/17/2015.
 */
 
public enum CashTransactionStatus {
    ACKNOWLEDGED("ACK"),
    PENDING("PEN"),
    COMPLETED("COM"),
    REJECTED("REJ");

    private String code;

    CashTransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CashTransactionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "NEG": return CashTransactionStatus.ACKNOWLEDGED;
            case "PEN": return CashTransactionStatus.PENDING;
            case "COM": return CashTransactionStatus.COMPLETED;
            case "CAN": return CashTransactionStatus.REJECTED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This is an invalid CashTransactionStatus code");
        }
    }
}
