package com.bitdubai.fermat_art_api.layer.actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum NotificationDescriptor implements FermatEnum {

    ACTOR_ASSET_NOT_FOUND   ("ANF"),
    ASKFORCONNECTION        ("ASK"),
    ACCEPTED                ("ACP"),
    CANCEL                  ("CAN"),
    DENIED                  ("DEN"),
    DISCONNECTED            ("DIS"),
    EXTENDED_KEY            ("EXT"),
    RECEIVED                ("REC"),
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
            case "ANF": return NotificationDescriptor.ACTOR_ASSET_NOT_FOUND;
            case "ASK": return NotificationDescriptor.ASKFORCONNECTION;
            case "ACP": return NotificationDescriptor.ACCEPTED;
            case "CAN": return NotificationDescriptor.CANCEL;
            case "DEN": return NotificationDescriptor.DENIED;
            case "DIS": return NotificationDescriptor.DISCONNECTED;
            case "EXT": return NotificationDescriptor.EXTENDED_KEY;
            case "REC": return NotificationDescriptor.RECEIVED;
            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This Code Is Not Valid for the NotificationDescriptor enum");
        }
    }
}
