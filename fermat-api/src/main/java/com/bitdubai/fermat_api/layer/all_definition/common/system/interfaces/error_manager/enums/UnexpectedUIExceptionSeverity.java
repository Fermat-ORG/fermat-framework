package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer
 */
public enum UnexpectedUIExceptionSeverity implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    CRASH          ("C"),
    NOT_IMPORTANT  ("NI"),
    UNSTABLE       ("U"),

    ;

    private final String code;

    UnexpectedUIExceptionSeverity(final String code){
        this.code = code;
    }

    public static UnexpectedUIExceptionSeverity getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "C":  return CRASH        ;
            case "NI": return NOT_IMPORTANT;
            case "U":  return UNSTABLE     ;

            default: throw new InvalidParameterException(
                    "Code: " + code,
                    "The received code is not valid for the UnexpectedUIExceptionSeverity enum"
            );

        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}