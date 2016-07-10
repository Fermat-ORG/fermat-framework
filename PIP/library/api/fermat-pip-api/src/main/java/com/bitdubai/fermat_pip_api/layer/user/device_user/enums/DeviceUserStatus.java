package com.bitdubai.fermat_pip_api.layer.user.device_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DeviceUserStatus</code> is a enum
 * that defines the status of the device users in the platform.
 * <p/>
 * Created by ciencias on 22.01.15.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 */
public enum DeviceUserStatus {

    LOGGED_IN("LI"),
    LOGGED_OUT("LO");

    private final String code;

    DeviceUserStatus(final String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static DeviceUserStatus getByCode(final String code) throws InvalidParameterException {

        switch (code) {
            case "LI":
                return DeviceUserStatus.LOGGED_IN;
            case "LO":
                return DeviceUserStatus.LOGGED_OUT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }

}
