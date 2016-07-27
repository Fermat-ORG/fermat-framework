package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 */

public enum NegotiationStatus implements FermatEnum {
    WAITING_FOR_BROKER("WFB"),
    WAITING_FOR_CUSTOMER("WFC"),
    WAITING_FOR_CLOSING("WFG"),
    SENT_TO_BROKER("STB"),
    SENT_TO_CUSTOMER("STC"),
    CLOSED("CLO"),
    CANCELLED("CAN");

    private String code;

    NegotiationStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NegotiationStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "WFB":
                return NegotiationStatus.WAITING_FOR_BROKER;
            case "WFC":
                return NegotiationStatus.WAITING_FOR_CUSTOMER;
            case "WFG":
                return NegotiationStatus.WAITING_FOR_CLOSING;
            case "STB":
                return NegotiationStatus.SENT_TO_BROKER;
            case "STC":
                return NegotiationStatus.SENT_TO_CUSTOMER;
            case "CLO":
                return NegotiationStatus.CLOSED;
            case "CAN":
                return NegotiationStatus.CANCELLED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ContactState enum");
        }
    }
}
