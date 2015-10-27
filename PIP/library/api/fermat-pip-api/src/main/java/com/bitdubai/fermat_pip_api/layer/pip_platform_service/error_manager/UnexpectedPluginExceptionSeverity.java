package com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 4/3/15.
 */
public enum UnexpectedPluginExceptionSeverity {
    NOT_IMPORTANT("NI"),
    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN("DSFWTP"),
    DISABLES_THIS_PLUGIN("DTP");

    private final String code;

    UnexpectedPluginExceptionSeverity(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static UnexpectedPluginExceptionSeverity getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "NI": return NOT_IMPORTANT;
            case "DSFWTP": return DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;
            case "DTP": return DISABLES_THIS_PLUGIN;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
}
