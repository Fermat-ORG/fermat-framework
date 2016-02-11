package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum NotificationDescriptor {

    ASKFORCONNECTION    ("ASK"),
    ACCEPTED            ("ACP"),
    CANCEL              ("CAN"),
    DISCONNECTED        ("DIS"),
    RECEIVED            ("REC"),
    DENIED              ("DEN"),
    ;

    private String code;

    NotificationDescriptor(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static NotificationDescriptor getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "ASK": return ASKFORCONNECTION;
            case "CAN": return CANCEL;
            case "ACP": return ACCEPTED;
            case "DIS": return DISCONNECTED;
            case "REC": return RECEIVED;
            case "DEN": return DENIED;
            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This Code Is Not Valid for the NotificationDescriptor enum");
        }
    }
}
