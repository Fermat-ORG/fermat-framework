package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 25.01.15.
 */
public enum DeviceDirectory {
    PLATFORM ("com/bitdubai/fermat_api"),
    LOCAL_USERS   ("localusers"),
    LOCAL_WALLETS   ("localwallets");

    private String code;

    DeviceDirectory(String code) {
        this.code = code;
    }

    public String getName ()   { return this.code; }

    //Modified by Manuel Perez on 03/08/2015
    public static DeviceDirectory getByCode(String code) throws InvalidParameterException{

        switch (code){

            case "com/bitdubai/fermat_api":
                return DeviceDirectory.PLATFORM;
            case "localusers":
                return DeviceDirectory.LOCAL_USERS;
            case "localwallets":
                return DeviceDirectory.LOCAL_WALLETS;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DeviceDirectory enum");

        }

    }

}