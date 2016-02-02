package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 21.01.15.
 * Modified by Manuel Perez on 03/08/2015.
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 31/01/2016.
 */
public enum TimeFrequency implements FermatEnum {

    DAILY   ("DY"),
    MONTHLY ("MN"),
    WEEKLY  ("WK"),
    YEARLY  ("YR"),

    ;

    private final String code;

    TimeFrequency(final String code) {

        this.code = code;
    }

    public static TimeFrequency getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "DY": return DAILY  ;
            case "MN": return MONTHLY;
            case "WK": return WEEKLY ;
            case "YR": return YEARLY ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The coide is not valid for the TimeFrequency enum."
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
