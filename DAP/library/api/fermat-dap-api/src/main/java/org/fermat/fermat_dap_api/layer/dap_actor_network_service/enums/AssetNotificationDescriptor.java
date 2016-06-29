package org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum AssetNotificationDescriptor implements FermatEnum {

    ACTOR_ASSET_NOT_FOUND("ANF"),
    ASKFORCONNECTION("ASK"),
    ACCEPTED("ACP"),
    CANCEL("CAN"),
    DENIED("DEN"),
    DISCONNECTED("DIS"),
    EXTENDED_KEY("EXT"),
    RECEIVED("REC"),;

    private String code;

    AssetNotificationDescriptor(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static AssetNotificationDescriptor getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ANF":
                return AssetNotificationDescriptor.ACTOR_ASSET_NOT_FOUND;
            case "ASK":
                return AssetNotificationDescriptor.ASKFORCONNECTION;
            case "ACP":
                return AssetNotificationDescriptor.ACCEPTED;
            case "CAN":
                return AssetNotificationDescriptor.CANCEL;
            case "DEN":
                return AssetNotificationDescriptor.DENIED;
            case "DIS":
                return AssetNotificationDescriptor.DISCONNECTED;
            case "EXT":
                return AssetNotificationDescriptor.EXTENDED_KEY;
            case "REC":
                return AssetNotificationDescriptor.RECEIVED;
            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This Code Is Not Valid for the NotificationDescriptor enum");
        }
    }
}
