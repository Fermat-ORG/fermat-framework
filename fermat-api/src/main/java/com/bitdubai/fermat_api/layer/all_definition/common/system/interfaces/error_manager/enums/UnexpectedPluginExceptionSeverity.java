package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 4/3/15.
 */
public enum UnexpectedPluginExceptionSeverity implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN ("DSFWTP"),
    DISABLES_THIS_PLUGIN                           ("DTP"),
    NOT_IMPORTANT                                  ("NI"),

    ;

    private final String code;

    UnexpectedPluginExceptionSeverity(final String code){
        this.code = code;
    }

    public static UnexpectedPluginExceptionSeverity getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "DSFWTP": return DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;
            case "DTP":    return DISABLES_THIS_PLUGIN;
            case "NI":     return NOT_IMPORTANT;

            default: throw new InvalidParameterException(
                    "Code: " + code,
                    "The received code is not valid for the UnexpectedPluginExceptionSeverity enum"
            );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
