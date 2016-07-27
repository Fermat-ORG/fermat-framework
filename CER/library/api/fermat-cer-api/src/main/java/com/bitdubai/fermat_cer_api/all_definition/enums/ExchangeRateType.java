package com.bitdubai.fermat_cer_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Alex on 28/12/2015.
 */
public enum ExchangeRateType implements FermatEnum {

    CURRENT("CU"),
    DAILY("DY"),
    //WEEK("WK"),
    //MONTH("MT"),
    //YEAR("YR"),
    ;

    private final String code;

    ExchangeRateType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ExchangeRateType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "CU":
                return CURRENT;
            case "DY":
                return DAILY;
            //case "WK":          return WEEK;
            //case "MT":          return MONTH;
            //case "YR":          return YEAR;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ExchangeRateType enum");
        }
    }
}