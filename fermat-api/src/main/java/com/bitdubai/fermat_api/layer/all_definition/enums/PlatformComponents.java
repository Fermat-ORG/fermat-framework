package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 4/4/15.
 */
public enum PlatformComponents implements FermatEnum {
    //Modified by Manuel Perez on 03/08/2015
    PLATFORM("PLAT"),
    PLATFORM_IDENTITY_MANAGER("PLATIM");

    private String code;

    PlatformComponents(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static PlatformComponents getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "PLAT":   return PlatformComponents.PLATFORM;
            case "PLATIM": return PlatformComponents.PLATFORM_IDENTITY_MANAGER;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the PlatformComponents enum");
        }
    }
}
