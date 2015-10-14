package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

public enum Actors {

    DEVICE_USER("DUS"),
    INTRA_USER("IUS"),
    EXTRA_USER("EUS"),
    SHOP("SHP"),
    ASSET_ISSUER("ASIS"),
    ASSET_USER("ASUS"),
    REDEEM_POINT("REPO"),;

    private String code;

    Actors(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Actors getByCode(String code)/*throws InvalidParameterException*/ {

        switch (code) {
            case "DUS":
                return Actors.DEVICE_USER;
            case "IUS":
                return Actors.INTRA_USER;
            case "EUS":
                return Actors.EXTRA_USER;
            case "SHP":
                return Actors.SHOP;
            case "ASIS":
                return Actors.ASSET_ISSUER;
            case "ASUS":
                return Actors.ASSET_USER;
            case "REPO":
                return Actors.REDEEM_POINT;
            //Modified by Manuel Perez on 03/08/2015
            //default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Actors enum");
        }

        /**
         * Return by default.
         */
        return Actors.DEVICE_USER;
    }
}
