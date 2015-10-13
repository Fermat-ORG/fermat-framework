package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 */
 
public enum NegotiationStatus {
    OPEN("OPE"),
    CLOSED("CLO"),
    CANCELLED("CAN");

    private String code;

    NegotiationStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static NegotiationStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "OPE": return NegotiationStatus.OPEN;
            case "CLO": return NegotiationStatus.CLOSED;
            case "CAN": return NegotiationStatus.CANCELLED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
