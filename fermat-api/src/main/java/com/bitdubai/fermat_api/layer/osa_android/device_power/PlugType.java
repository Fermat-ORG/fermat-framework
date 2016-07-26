package com.bitdubai.fermat_api.layer.osa_android.device_power;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by toshiba on 04/05/2015.
 */
public enum PlugType {

    //Modified by Manuel Perez on 05/08/2015
    PLUGGED_AC("PAC"),
    PLUGGED_USB("PUSB");

    private String code;

    PlugType(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static PlugType getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "PAC":
                return PlugType.PLUGGED_AC;
            case "PUSB":
                return PlugType.PLUGGED_USB;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the PlugType enum");


        }

    }

}
