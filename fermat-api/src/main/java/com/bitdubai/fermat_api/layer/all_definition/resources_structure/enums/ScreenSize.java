package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */
public enum ScreenSize {

    XSMALL("xsmall"),
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large"),
    XLARGE("xlarge");

    private final String code;

    ScreenSize(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ScreenSize getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "xsmall":
                return ScreenSize.XSMALL;
            case "small":
                return ScreenSize.SMALL;
            case "normal":
                return ScreenSize.MEDIUM;
            case "large":
                return ScreenSize.LARGE;
            case "xlarge":
                return ScreenSize.XLARGE;
            //Modified by Manuel Perez on 04/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ScreenSize enum");

        }

        /**
         * Return by default.
         */
        //return ScreenSize.MEDIUM;
    }

}
