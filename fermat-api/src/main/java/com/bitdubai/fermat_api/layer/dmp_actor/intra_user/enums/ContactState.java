package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState</code>
 * list the states an intra user can be asigned
 */
public enum ContactState {
    PENDING_YOUR_ACCEPTANCE("PYA"),
    BLOCKED ("BCK"),
    LOCALLY_DENIED ("DEN"),
    CANCELLED ("CAN"),
    CONNECTED ("CTC"),
    LOCALLY_DISCONNECTED("DIS"),
    PENDING_HIS_ACCEPTANCE ("PHA");

    private String code;

    ContactState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ContactState getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "PYA": return ContactState.PENDING_YOUR_ACCEPTANCE;
            case "BCK": return ContactState.BLOCKED;
            case "DEN": return ContactState.LOCALLY_DENIED;
            case "CAN": return ContactState.CANCELLED;
            case "CTC": return ContactState.CONNECTED;
            case "DIS": return ContactState.LOCALLY_DISCONNECTED;
            case "PHA": return ContactState.PENDING_HIS_ACCEPTANCE;
            //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
