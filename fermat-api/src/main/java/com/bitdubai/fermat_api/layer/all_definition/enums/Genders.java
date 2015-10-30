package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>Genders</code>
 * list the Genders for intra User Wallet and Aseet Issuer, User, Redeem Point can be assigned.
 */

public enum Genders implements FermatEnum {

    MALE("M"),
    FEMALE("F"),
    INDEFINITE("I");

    private String code;

    Genders(String code) {
        this.code = code;
    }

    public static Genders getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "M": return Genders.MALE;
            case "F": return Genders.FEMALE;
            case "I": return Genders.INDEFINITE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
