package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 */
public enum RequestStatus {
    ACCEPTED("ACC"),
    POSTPONED("POS"),
    IN_PROCESS ("INP");

    private String code;

    RequestStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static RequestStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ACC": return RequestStatus.ACCEPTED;
            case "POS": return RequestStatus.POSTPONED;
            case "INP": return RequestStatus.IN_PROCESS;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
