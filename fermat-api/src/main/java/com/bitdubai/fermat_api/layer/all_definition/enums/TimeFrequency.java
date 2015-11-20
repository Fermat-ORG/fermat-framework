package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 21.01.15.
 */
public enum TimeFrequency implements FermatEnum {
    //Modified by Manuel Perez on 03/08/2015
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    YEARLY("YEARLY");

    private String code;

    TimeFrequency(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static TimeFrequency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "DAILY":   return TimeFrequency.DAILY;
            case "WEEKLY":  return TimeFrequency.WEEKLY;
            case "MONTHLY": return TimeFrequency.MONTHLY;
            case "YEARLY":  return TimeFrequency.YEARLY;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TimeFrequency enum");
        }
    }
}
