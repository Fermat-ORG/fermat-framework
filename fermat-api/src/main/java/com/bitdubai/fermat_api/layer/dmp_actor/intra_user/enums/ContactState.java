package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState</code>
 * list the states an intra user can be asigned
 */
public enum ContactState {
    PENDING_YOUR_ACCEPTANCE("PYA"),
    BLOCKED ("BCK"),
    CONTACT ("CTC"),
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
            case "CTC": return ContactState.CONTACT;
            case "PHA": return ContactState.PENDING_HIS_ACCEPTANCE;
            //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
