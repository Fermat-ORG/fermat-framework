package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState</code>
 * list the states an intra user can be asigned
 */
public enum ContactState {
    PENDING_LOCALLY_ACCEPTANCE("PYA"),
    BLOCKED ("BCK"),
    DENIED_LOCALLY ("DEN"),
    CANCELLED ("CAN"),
    CONNECTED ("CTC"),
    DISCONNECTED_LOCALLY("DSL"),
    DISCONNECTED_REMOTELY("DSR"),
    PENDING_REMOTELY_ACCEPTANCE ("PRA");

    private String code;

    ContactState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ContactState getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "PYA": return ContactState.PENDING_LOCALLY_ACCEPTANCE;
            case "BCK": return ContactState.BLOCKED;
            case "DEN": return ContactState.DENIED_LOCALLY;
            case "CAN": return ContactState.CANCELLED;
            case "CTC": return ContactState.CONNECTED;
            case "DIS": return ContactState.DISCONNECTED_LOCALLY;
            case "DSR": return ContactState.DISCONNECTED_REMOTELY;
            case "PRA": return ContactState.PENDING_REMOTELY_ACCEPTANCE;
            //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
