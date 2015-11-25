package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 21/11/15.
 */

public enum CryptoTransactionStatus {
    ACKNOWLEDGED("ACK"),
    PENDING("PEN"),
    CONFIRMED("CON"),
    REJECTED("REJ");

    private String code;

    CryptoTransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CryptoTransactionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ACK":
                return CryptoTransactionStatus.ACKNOWLEDGED;
            case "PEN":
                return CryptoTransactionStatus.PENDING;
            case "CON":
                return CryptoTransactionStatus.CONFIRMED;
            case "REJ":
                return CryptoTransactionStatus.REJECTED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This is an invalid CashTransactionStatus code");
        }
    }
}