package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory</code>
 * Lists all device directories in Fermat.
 * Created by ciencias on 25.01.15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 20/10/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 01/12/2015.
 */
public enum DeviceDirectory implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    LOCAL_USERS("LUS", "localusers"),
    LOCAL_WALLETS("LWA", "localwallets"),
    SYSTEM("SYS", "com/bitdubai/fermat_api"),;

    private final String code;
    private final String name;

    DeviceDirectory(final String code,
                    final String name) {
        this.code = code;
        this.name = name;
    }

    public static DeviceDirectory getByName(String name) throws InvalidParameterException {

        switch (name) {
            case "com/bitdubai/fermat_api":
                return SYSTEM;
            case "localusers":
                return LOCAL_USERS;
            case "localwallets":
                return LOCAL_WALLETS;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Name received: ").append(name).toString(),
                        "The name received is not valid for the DeviceDirectory enum"
                );
        }
    }

    public static DeviceDirectory getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "LUS":
                return LOCAL_USERS;
            case "LWA":
                return LOCAL_WALLETS;
            case "SYS":
                return SYSTEM;
            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code received: ").append(code).toString(),
                        "The code received is not valid for the DeviceDirectory enum"
                );
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

}