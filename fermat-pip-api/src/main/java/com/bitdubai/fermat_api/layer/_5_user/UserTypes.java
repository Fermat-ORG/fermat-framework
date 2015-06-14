package com.bitdubai.fermat_api.layer._5_user;

/**
 * Created by ciencias on 3/18/15.
 */
public enum UserTypes {

    DEVICE_USER ("DUS"),
    EXTRA_USER ("EUS"),
    INTRA_USER ("IUS");

    private final String code;

    UserTypes(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static UserTypes getByCode(String code) {

        switch (code) {
            case "DUS": return UserTypes.DEVICE_USER;
            case "EUS": return UserTypes.EXTRA_USER;
            case "IUS": return UserTypes.INTRA_USER;
        }

        /**
         * Return by default.
         */
        return UserTypes.DEVICE_USER;
    }
}



