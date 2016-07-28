package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.08.01..
 */

public enum ScreenOrientation {
    PORTRAIT("portrait"),
    LANDSCAPE("landscape");

    private final String code;

    ScreenOrientation(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ScreenOrientation getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "portrait":
                return ScreenOrientation.PORTRAIT;
            case "landscape":
                return ScreenOrientation.LANDSCAPE;
            //Modified by Manuel Perez on 04/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ScreenOrientation enum");

        }

        /**
         * Return by default.
         */
        //return ScreenOrientation.PORTRAIT;
    }
}
