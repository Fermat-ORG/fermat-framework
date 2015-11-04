package com.bitdubai.fermat_bnk_api.all_definition.enums;

import com.bitdubai.fermat_bnk_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum BankAccountType {
    CURRENT("CUR"),
    SAVING("SAV");

    private String code;

    BankAccountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BankAccountType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CUR": return BankAccountType.CURRENT;
            case "SAV": return BankAccountType.SAVING;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankAccountType enum");
        }
    }
}
