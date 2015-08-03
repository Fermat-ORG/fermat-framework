package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */
public enum ScreenSize {

    WATCH("watch"),
    SMALL ("small"),
    NORMAL ("normal"),
    LARGE ("large"),
    XLARGE ("xlarge");

    private final String code;

    ScreenSize(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static ScreenSize getByCode(String code) {

        switch (code) {
            case "watch": return ScreenSize.WATCH;
            case "small": return ScreenSize.SMALL;
            case "normal": return ScreenSize.NORMAL;
            case "large": return ScreenSize.LARGE;
            case "xlarge": return ScreenSize.XLARGE;
        }

        /**
         * Return by default.
         */
        return ScreenSize.NORMAL;
    }

//    public static ScreenSize getBySyze(int weight.int height) {
//
//        switch (code) {
//            case "watch": return ScreenSize.WATCH;
//            case "small": return ScreenSize.SMALL;
//            case "normal": return ScreenSize.NORMAL;
//            case "large": return ScreenSize.LARGE;
//            case "xlarge": return ScreenSize.XLARGE;
//        }
//
//        /**
//         * Return by default.
//         */
//        return ScreenSize.NORMAL;
//    }


}
