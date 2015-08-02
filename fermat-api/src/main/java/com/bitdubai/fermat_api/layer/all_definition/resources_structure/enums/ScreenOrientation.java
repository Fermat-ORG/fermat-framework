package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

/**
 * Created by Matias Furszyfer on 2015.08.01..
 */

public enum ScreenOrientation {
    PORTRAIT ("portrait"),
    LANDSCAPE ("landscape");

    private final String code;

    ScreenOrientation(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static ScreenOrientation getByCode(String code) {

        switch (code) {
            case "portrait": return ScreenOrientation.PORTRAIT;
            case "landscape": return ScreenOrientation.LANDSCAPE;
        }

        /**
         * Return by default.
         */
        return ScreenOrientation.PORTRAIT;
    }
}
