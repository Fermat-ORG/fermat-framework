package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Genders</code>
 * Lists all the Genders for intra User Wallet and Aseet Issuer.
 * User, Redeem Point can be assigned.
 * <p/>
 * Updated by pmgesualdi - (pmgesualdi@hotmail.com) on 18/11/2015.
 */
public enum Genders implements FermatEnum {
    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    FEMALE("F"),
    INDEFINITE("I"),
    MALE("M");

    private final String code;

    Genders(final String code) {
        this.code = code;
    }

    public static Genders getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "F":
                return Genders.FEMALE;
            case "I":
                return Genders.INDEFINITE;
            case "M":
                return Genders.MALE;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the Genders enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
