package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Enums the types of Compatibility in Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 24/09/2015.
 */
public enum Compatibility implements FermatEnum {

    COMPATIBLE  ("COMP"),
    INCOMPATIBLE("INCO"),
    NONE        ("NONE");

    private String code;

    Compatibility(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static Compatibility getByCode(String code) throws InvalidParameterException {

        for (Compatibility vault : Compatibility.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the Compatibility enum.");
    }
}
