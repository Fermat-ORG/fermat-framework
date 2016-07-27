package com.bitdubai.fermat_cht_api.layer.network_service.chat.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by root on 06/01/16.
 */
public enum DistributionStatus implements FermatEnum {


    DELIVERED("DELIVERED"),
    SENT("SENT"),
    DELIVERING("DELIVERING"),
    INCOMING_CHAT("INCHAT"),
    OUTGOING_CHAT("OUTCHT"),
    INCOMING_MSG("INMSG"),
    OUTGOING_MSG("OUTMSG"),
    CANNOT_SEND("CSED");
    String code;

    DistributionStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static DistributionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DELIVERED":
                return DistributionStatus.DELIVERED;
            case "DELIVERING":
                return DistributionStatus.DELIVERING;
            case "INCHAT":
                return DistributionStatus.INCOMING_CHAT;
            case "OUTCHT":
                return DistributionStatus.OUTGOING_CHAT;
            case "INMSG":
                return DistributionStatus.INCOMING_MSG;
            case "OUTMSG":
                return DistributionStatus.OUTGOING_MSG;
            case "CSED":
                return DistributionStatus.CANNOT_SEND;
            case "SENT":
                return DistributionStatus.SENT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the EventStatus enum.");
        }
    }
}
