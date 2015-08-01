package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */
public enum ScreenSize {

    SMALL ("small"),
    MEDIUM ("medium"),
    HIGH ("high");

    private final String code;

    ScreenSize(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static ScreenSize getByCode(String code) {

        switch (code) {
            case "DUS": return ScreenSize.SMALL;
            case "IUS": return ScreenSize.MEDIUM;
            case "EUS": return ScreenSize.HIGH;
        }

        /**
         * Return by default.
         */
        return ScreenSize.MEDIUM;
    }
}
