package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Natalia on 07/04/2015.
 */
public enum UnexpectedAddonsExceptionSeverity implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS("DSFWTA"),
    DISABLES_THIS_ADDONS("DTA"),
    NOT_IMPORTANT("NI"),;

    private final String code;

    UnexpectedAddonsExceptionSeverity(final String code) {
        this.code = code;
    }

    public static UnexpectedAddonsExceptionSeverity getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "NI":
                return NOT_IMPORTANT;
            case "DSFWTA":
                return DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS;
            case "DTA":
                return DISABLES_THIS_ADDONS;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code: ").append(code).toString(),
                        "The received code is not valid for the UnexpectedAddonsExceptionSeverity enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
