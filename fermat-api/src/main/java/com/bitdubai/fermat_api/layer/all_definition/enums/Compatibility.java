package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility</code>
 * Enums the types of Compatibility in Fermat.
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 24/09/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum Compatibility implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    COMPATIBLE("COMP"),
    INCOMPATIBLE("INCO"),
    NONE("NONE");

    private final String code;

    Compatibility(final String code) {
        this.code = code;
    }

    public static Compatibility getByCode(String code) throws InvalidParameterException {
        for (Compatibility vault : Compatibility.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(
                new StringBuilder().append("Code Received: ").append(code).toString(),
                "This code is not valid for the Compatibility enum."
        );
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
