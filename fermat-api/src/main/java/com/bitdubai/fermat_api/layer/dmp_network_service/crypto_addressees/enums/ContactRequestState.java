package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums.ContactRequestState</code>
 * represents the state of the Contact Request.
 */
public enum ContactRequestState {
    PENDING_REMOTE_ACCEPTANCE ("PRA"),
    PENDING_LOCALLY_ACCEPTANCE ("PLA"),
    ACCEPTED ("ACC");

    private String code;

    ContactRequestState(String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static ContactRequestState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "PRA": return ContactRequestState.PENDING_REMOTE_ACCEPTANCE;
            case "PLA": return ContactRequestState.PENDING_LOCALLY_ACCEPTANCE;
            case "ACC": return ContactRequestState.ACCEPTED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactRequestState enum");

        }
    }
}
