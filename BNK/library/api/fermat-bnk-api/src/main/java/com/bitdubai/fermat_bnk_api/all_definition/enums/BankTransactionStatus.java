package com.bitdubai.fermat_bnk_api.all_definition.enums;

import com.bitdubai.fermat_bnk_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 23.09.15.
 */
 
public enum BankTransactionStatus {
    ACKNOWLEDGE("ACK"),
    PENDING("PEN"),
    CONFIRMED("CON"),
    REJECTED("REJ");

    private String code;

    BankTransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BankTransactionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ACK": return BankTransactionStatus.ACKNOWLEDGE;
            case "PEN": return BankTransactionStatus.PENDING;
            case "CON": return BankTransactionStatus.CONFIRMED;
            case "REJ": return BankTransactionStatus.REJECTED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankTransactionStatus enum");
        }
    }
}
