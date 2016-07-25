package com.bitdubai.fermat_api.layer.osa_android.device_power;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Natalia on 04/05/2015.
 */
public enum PowerStatus {

    //Modified by Manuel Perez on 05/08/2015
    CHARGING("CHAR"),
    DISCHARGING("DCHAR"),
    FULL("FULL"),
    NOT_CHARGING("NCHAR");

    private String code;

    PowerStatus(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static PowerStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "CHAR":
                return PowerStatus.CHARGING;
            case "DCHAR":
                return PowerStatus.DISCHARGING;
            case "FULL":
                return PowerStatus.FULL;
            case "NCHAR":
                return PowerStatus.NOT_CHARGING;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the PowerStatus enum");

        }

    }

}
