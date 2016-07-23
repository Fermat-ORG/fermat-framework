package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.Developers</code>
 * Lists all the developers in Fermat.
 * Created by loui on 22/02/15.
 * Modified by Manuel Perez on 03/08/2015
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 01/12/2015.
 */
public enum Developers implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITDUBAI("BitDubai");

    private final String code;

    Developers(final String code) {
        this.code = code;
    }

    public static Developers getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BitDubai":
                return Developers.BITDUBAI;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the Developers enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
