package com.bitdubai.fermat_pip_api.layer.pip_user.device_user.enums;

/**
 * <p>The enum <code>com.bitdubai.fermat_pip_api.layer.pip_user.device_user.enums.DeviceUserStatus</code> is a enum
 * that defines the status of the device users in the platform.
 * <p/>
 * Created by ciencias on 22.01.15.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 */
public enum DeviceUserStatus {

    LOGGED_IN("logged_in"),
    LOGGED_OUT("logged_out");

    private final String code;

    DeviceUserStatus(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static DeviceUserStatus getByCode(String code) {

        switch (code) {
            case "logged_in":
                return DeviceUserStatus.LOGGED_IN;
            case "logged_out":
                return DeviceUserStatus.LOGGED_OUT;
        }

        /**
         * Return by default logged out TODO check.
         */
        return LOGGED_OUT;
    }

}
