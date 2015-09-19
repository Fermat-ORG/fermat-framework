package com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Natalia on 07/04/2015.
 */
public enum UnexpectedAddonsExceptionSeverity {
    NOT_IMPORTANT("NI"),
    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS("DSFWTA"),
    DISABLES_THIS_ADDONS("DTA");

    private final String code;

    UnexpectedAddonsExceptionSeverity(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static UnexpectedAddonsExceptionSeverity getByCode(final String code) throws InvalidParameterException {
        switch (code){
            case "NI": return UnexpectedAddonsExceptionSeverity.NOT_IMPORTANT;
            case "DSFWTA": return UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS;
            case "DTA": return UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
}
