package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>ConnectionState</code>
 * list the states for intra User Wllet and Aseet Issuer, User, Redeem Point can be assigned.
 */
public enum ConnectionState implements FermatEnum {

    PENDING_LOCALLY_ACCEPTANCE  ("PYA"),
    BLOCKED                     ("BCK"),
    DENIED_LOCALLY              ("DEN"),
    CANCELLED                   ("CAN"),
    CONNECTED                   ("CTC"),
    DISCONNECTED_LOCALLY        ("DSL"),
    DISCONNECTED_REMOTELY       ("DSR"),
    PENDING_REMOTELY_ACCEPTANCE ("PRA");

    private String code;

    ConnectionState(String code) {
        this.code = code;
    }

    public static ConnectionState getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "PYA": return PENDING_LOCALLY_ACCEPTANCE ;
            case "BCK": return BLOCKED                    ;
            case "DEN": return DENIED_LOCALLY             ;
            case "CAN": return CANCELLED                  ;
            case "CTC": return CONNECTED                  ;
            case "DSL": return DISCONNECTED_LOCALLY       ;
            case "DSR": return DISCONNECTED_REMOTELY      ;
            case "PRA": return PENDING_REMOTELY_ACCEPTANCE;
            default:    throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
