package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by jorge on 12-10-2015.
 */
public enum ClauseStatus {
    DRAFT("DRA"),
    SENTTOBROKER("STB"),
    WAITINGFORBROKER("WFB"),
    SENTTOCUSTOMER("STC"),
    WAITINGFORCUSTOMER("WFC"),
    AGREED("AGR"),
    REJECTED("REJ");

    private String code;

    ClauseStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ClauseStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DRA": return DRAFT;
            case "STB": return SENTTOBROKER;
            case "WFB": return WAITINGFORBROKER;
            case "STC": return SENTTOCUSTOMER;
            case "WFC": return WAITINGFORCUSTOMER;
            case "AGR": return AGREED;
            case "REJ": return REJECTED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ClauseStatus enum");
        }
    }
}
