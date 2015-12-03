package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.PlatformFileName</code>
 * Lists all the names of the Platforms files in Fermat.
 * Created by ciencias on 22.01.15.
 * Modified by Manuel Perez on 03/08/2015
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 01/12/2015.
 */
public enum PlatformFileName implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    LAST_STATE  ("Platform_Last_State")

    ;

    private final String code;

    PlatformFileName(final String code) {
        this.code = code;
    }

    public static PlatformFileName getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "Platform_Last_State": return PlatformFileName.LAST_STATE;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the PlatformFileName enum");
        }
    }

    @Override
    public String getCode() {
        return code;
    }

}
