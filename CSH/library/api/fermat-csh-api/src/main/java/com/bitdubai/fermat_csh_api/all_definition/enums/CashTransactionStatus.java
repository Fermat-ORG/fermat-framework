package com.bitdubai.fermat_csh_api.all_definition.enums;

import com.bitdubai.fermat_csh_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 23.09.15.
 */
 
public enum CashTransactionStatus {
    NEGOTIATION("NEG"),
    PAUSED("PAU"),
    PENDING("PEN"),
    COMPLETED("COM"),
    CANCELLED ("CAN");

    private String code;

    CashTransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CashTransactionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "NEG": return CashTransactionStatus.NEGOTIATION;
            case "PAU": return CashTransactionStatus.PAUSED;
            case "PEN": return CashTransactionStatus.PENDING;
            case "COM": return CashTransactionStatus.COMPLETED;
            case "CAN": return CashTransactionStatus.CANCELLED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CashTransactionStatus enum");
        }
    }
}
