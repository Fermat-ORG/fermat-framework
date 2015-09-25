package com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 4/4/15.
 */
public enum UnexpectedPlatformExceptionSeverity {
    NOT_IMPORTANT("NI"),
    DISABLES_ONE_PLUGIN("DOP"),
    DISABLES_ALL_PLUGINS("DAP"),
    DISABLES_ALL_THE_PLATFORM("DATP");

    private final String code;

    UnexpectedPlatformExceptionSeverity(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static UnexpectedPlatformExceptionSeverity getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "NI": return UnexpectedPlatformExceptionSeverity.NOT_IMPORTANT;
            case "DOP": return UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN;
            case "DAP": return UnexpectedPlatformExceptionSeverity.DISABLES_ALL_PLUGINS;
            case "DATP": return UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
}
