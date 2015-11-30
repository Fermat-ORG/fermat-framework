package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Enums the types of Compatibility in Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 24/09/2015.
 * Updated by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum Compatibility implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    COMPATIBLE  ("COMP"),
    INCOMPATIBLE("INCO"),
    NONE        ("NONE");

    private final String code;

    Compatibility(final String code) {
        this.code = code;
    }

    public static Compatibility getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "COMP":    return Compatibility.COMPATIBLE;
            case "INCO":    return Compatibility.INCOMPATIBLE;
            case "NONE":    return Compatibility.NONE;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the Compatibility enum."
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
