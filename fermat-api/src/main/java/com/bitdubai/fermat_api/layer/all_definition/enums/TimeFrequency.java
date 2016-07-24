package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 21.01.15.
 * Modified by Manuel Perez on 03/08/2015.
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 31/01/2016.
 * Updated by Nelson Ramirez (nelsonalfo@gmail.com) on 26/02/2016.
 */
public enum TimeFrequency implements FermatEnum {

    DAILY("DY", "Daily"),
    MONTHLY("MN", "Monthly"),
    NONE("NN", "None"),
    WEEKLY("WK", "Weekly"),
    YEARLY("YR", "Yearly"),;

    private final String code, friendlyName;

    TimeFrequency(final String code, final String friendlyName) {
        this.code = code;
        this.friendlyName = friendlyName;
    }

    public static TimeFrequency getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "DY":
                return DAILY;
            case "MN":
                return MONTHLY;
            case "NN":
                return NONE;
            case "WK":
                return WEEKLY;
            case "YR":
                return YEARLY;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code is not valid for the TimeFrequency enum."
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
