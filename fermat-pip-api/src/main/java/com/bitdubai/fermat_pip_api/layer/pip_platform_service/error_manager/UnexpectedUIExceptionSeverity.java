package com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer
 */
public enum UnexpectedUIExceptionSeverity {
    NOT_IMPORTANT("NI"),
    UNSTABLE("U"),
    CRASH("C");

    private final String code;

    UnexpectedUIExceptionSeverity(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static UnexpectedUIExceptionSeverity getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "NI": return NOT_IMPORTANT;
            case "U": return UNSTABLE;
            case "C": return CRASH;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
}