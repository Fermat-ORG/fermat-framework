package com.bitdubai.fermat_bnk_api.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 * Modified by Alejandro Bicelis on 07.04.16
 */
 
public enum BankAccountType {
    CHECKING("CHC", "Checking"),
    SAVINGS("SAV", "Savings");

    private String code, friendlyName;

    BankAccountType(String code, String friendlyName) {
        this.code = code;
        this.friendlyName = friendlyName;
    }

    public String getCode() {
        return this.code;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public static BankAccountType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CHC": return BankAccountType.CHECKING;
            case "SAV": return BankAccountType.SAVINGS;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankAccountType enum");
        }
    }
}
