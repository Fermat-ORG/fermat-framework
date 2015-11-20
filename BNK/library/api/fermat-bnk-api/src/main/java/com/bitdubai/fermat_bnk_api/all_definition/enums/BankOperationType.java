package com.bitdubai.fermat_bnk_api.all_definition.enums;

import com.bitdubai.fermat_bnk_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public enum BankOperationType {

    DEPOSIT("DEP"),
    TRANSFER("TRA");

    private String code;

    BankOperationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BankOperationType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DEP": return BankOperationType.DEPOSIT;
            case "TRA": return BankOperationType.TRANSFER;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankOperationType enum");
        }
    }

}
