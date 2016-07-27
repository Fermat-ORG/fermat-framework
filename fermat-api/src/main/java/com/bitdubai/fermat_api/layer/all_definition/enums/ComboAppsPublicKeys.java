package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2016.06.02..
 */
public enum ComboAppsPublicKeys implements FermatEnum {

    CHT_IDENTITY_COMMUNITY("CHT_I_C");

    String code;

    ComboAppsPublicKeys(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }


    public static ComboAppsPublicKeys getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CHT_I_C":
                return CHT_IDENTITY_COMMUNITY;
            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }
}
