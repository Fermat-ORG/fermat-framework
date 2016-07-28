package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents</code>
 * Lists all Platform Components in Fermat.
 * Created by ciencias on 4/4/15.
 * Modified by Manuel Perez on 03/08/2015
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 01/12/2015.
 */

public enum PlatformComponents implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    PLATFORM("PLAT"),
    PLATFORM_IDENTITY_MANAGER("PLATIM");

    private final String code;

    PlatformComponents(final String code) {
        this.code = code;
    }

    public static PlatformComponents getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "PLAT":
                return PlatformComponents.PLATFORM;
            case "PLATIM":
                return PlatformComponents.PLATFORM_IDENTITY_MANAGER;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the PlatformComponents enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
