package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum AssetNotificationDescriptor implements FermatEnum {

    ASKFORCONNECTION        ("ASK"),
    ACCEPTED                ("ACP"),
    CANCEL                  ("CAN"),
    DISCONNECTED            ("DIS"),
    RECEIVED                ("REC"),
    DENIED                  ("DEN"),
    ACTOR_ASSET_NOT_FOUND   ("ANF"),
    ;

    private String code;

    AssetNotificationDescriptor(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static AssetNotificationDescriptor getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "ASK": return AssetNotificationDescriptor.ASKFORCONNECTION;
            case "CAN": return AssetNotificationDescriptor.CANCEL;
            case "ACP": return AssetNotificationDescriptor.ACCEPTED;
            case "DIS": return AssetNotificationDescriptor.DISCONNECTED;
            case "REC": return AssetNotificationDescriptor.RECEIVED;
            case "DEN": return AssetNotificationDescriptor.DENIED;
            case "ANF": return AssetNotificationDescriptor.ACTOR_ASSET_NOT_FOUND;
            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This Code Is Not Valid for the NotificationDescriptor enum");
        }
    }
}
