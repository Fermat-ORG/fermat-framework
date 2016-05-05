package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 4/4/15.
 */
public enum UnexpectedPlatformExceptionSeverity implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    DISABLES_ALL_THE_PLATFORM("DATP"),
    DISABLES_ALL_PLUGINS("DAP"),
    DISABLES_ONE_PLUGIN("DOP"),
    NOT_IMPORTANT("NI"),

    ;

    private final String code;

    UnexpectedPlatformExceptionSeverity(final String code){
        this.code = code;
    }


    public static UnexpectedPlatformExceptionSeverity getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "DATP": return DISABLES_ALL_THE_PLATFORM;
            case "DAP":  return DISABLES_ALL_PLUGINS     ;
            case "DOP":  return DISABLES_ONE_PLUGIN      ;
            case "NI":   return NOT_IMPORTANT            ;

            default: throw new InvalidParameterException(
                    "Code: " + code,
                    "The received code is not valid for the UnexpectedPlatformExceptionSeverity enum"
            );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
