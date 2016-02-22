package com.bitdubai.fermat_bnk_api.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum BankAccountType {
    CURRENT("CUR", "Current"),
    SAVING("SAV", "Saving");

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
            case "CUR": return BankAccountType.CURRENT;
            case "SAV": return BankAccountType.SAVING;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankAccountType enum");
        }
    }
}
