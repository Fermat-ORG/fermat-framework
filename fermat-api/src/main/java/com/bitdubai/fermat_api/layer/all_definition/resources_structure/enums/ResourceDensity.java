package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */

public enum ResourceDensity implements Serializable {

    LDPI ("ldpi"),
    MDPI ("mdpi"),
    HDPI ("hdpi");

    private final String code;

    ResourceDensity(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static ResourceDensity getByCode(String code) {

        switch (code) {
            case "ldpi": return ResourceDensity.LDPI;
            case "mdpi": return ResourceDensity.MDPI;
            case "hdpi": return ResourceDensity.HDPI;
        }

        /**
         * Return by default.
         */
        return ResourceDensity.MDPI;
    }
}
