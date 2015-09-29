package com.bitdubai.fermat_bnk_api.all_definition.enums;

import com.bitdubai.fermat_bnk_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 28.09.15.
 */
public enum BankMoneyTransactionType {
    MAKE("MAK"),
    RECEIVE("REC");

    private String code;

    BankMoneyTransactionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BankMoneyTransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "MAK": return BankMoneyTransactionType.MAKE;
            case "REC": return BankMoneyTransactionType.RECEIVE;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankMoneyTransactionType enum");
        }
    }
}
