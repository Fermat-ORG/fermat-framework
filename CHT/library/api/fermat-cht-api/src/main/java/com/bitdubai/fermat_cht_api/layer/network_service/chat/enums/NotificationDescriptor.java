package com.bitdubai.fermat_cht_api.layer.network_service.chat.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum NotificationDescriptor {
    //Modified by Manuel Perez on 05/08/2015
    ASKFORACCEPTANCE("ASK"),
    INTRA_USER_NOT_FOUND("IUNF"),
    ACCEPTED("ACP"),
    CANCEL("CAN"),
    DISCONNECTED("DIS"),
    RECEIVED("REC"),
    DENIED("DEN");

    private String code;

    NotificationDescriptor(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static NotificationDescriptor getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "ASK":
                return NotificationDescriptor.ASKFORACCEPTANCE;
            case "CAN":
                return NotificationDescriptor.CANCEL;
            case "ACP":
                return NotificationDescriptor.ACCEPTED;
            case "DIS":
                return NotificationDescriptor.DISCONNECTED;
            case "REC":
                return NotificationDescriptor.RECEIVED;
            case "DEN":
                return NotificationDescriptor.DENIED;
            case "IUNF":
                return NotificationDescriptor.INTRA_USER_NOT_FOUND;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the NotificationDescriptor enum");


        }

    }

}
