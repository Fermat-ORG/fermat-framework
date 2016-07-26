package com.bitdubai.fermat_api.layer.all_definition.common.system.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by natalia on 19/01/16.
 */
public enum NetworkStatus {

    CONNECTED("CON"),
    DISCONNECTED("DIS");

    private String code;

    NetworkStatus(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static NetworkStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "CON":
                return NetworkStatus.CONNECTED;
            case "DIS":
                return NetworkStatus.DISCONNECTED;

            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the NotificationDescriptor enum");


        }

    }

}
