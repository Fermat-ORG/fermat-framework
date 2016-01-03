package com.bitdubai.fermat_cer_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Alex on 28/12/2015.
 */
public enum TimeUnit implements FermatEnum {

    HOUR("HR"),
    DAY("DY"),
    WEEK("WK"),
    MONTH("MT"),
    YEAR("YR"),
    ;

    private final String code;

    TimeUnit(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static TimeUnit getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "HR":          return HOUR;
            case "DY":          return DAY;
            case "WK":          return WEEK;
            case "MT":          return MONTH;
            case "YR":          return YEAR;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TimeUnit enum");
        }
    }
}