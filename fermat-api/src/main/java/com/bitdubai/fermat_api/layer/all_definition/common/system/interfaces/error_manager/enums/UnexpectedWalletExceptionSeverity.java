package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Natalia on 07/04/2015.
 */
public enum UnexpectedWalletExceptionSeverity implements FermatEnum {

    // TODO MAKE THIS COMPATIBLE WITH OTHER TYPES OF OS - FRAGMENTS ONLY BELONGS TO ANDROID.

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT  ("DSFWTF"),
    DISABLES_THIS_FRAGMENT                            ("DTF"),
    NOT_IMPORTANT                                     ("NI"),

    ;

    private final String code;

    UnexpectedWalletExceptionSeverity(final String code){
        this.code = code;
    }


    public static UnexpectedWalletExceptionSeverity getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "DSFWTF": return DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
            case "DTF":    return DISABLES_THIS_FRAGMENT                          ;
            case "NI":     return NOT_IMPORTANT                                   ;

            default: throw new InvalidParameterException(
                    "Code: " + code,
                    "The received code is not valid for the UnexpectedWalletExceptionSeverity enum"
            );
        }
    }

    @Override
    public String getCode(){
        return code;
    }

}