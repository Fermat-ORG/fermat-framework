package com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Natalia on 07/04/2015.
 */
public enum UnexpectedWalletExceptionSeverity {
    NOT_IMPORTANT("NI"),
    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT("DSFWTF"),
    DISABLES_THIS_FRAGMENT("DTF");

    private final String code;

    UnexpectedWalletExceptionSeverity(final String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static UnexpectedWalletExceptionSeverity getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "NI": return NOT_IMPORTANT;
            case "DSFWTF": return DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
            case "DTF": return DISABLES_THIS_FRAGMENT;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
}