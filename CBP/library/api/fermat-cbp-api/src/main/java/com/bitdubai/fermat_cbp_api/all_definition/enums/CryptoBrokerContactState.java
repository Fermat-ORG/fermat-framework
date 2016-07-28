package com.bitdubai.fermat_cbp_api.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by natalia on 16/09/15.
 */
public enum CryptoBrokerContactState implements FermatEnum {

    PENDING_LOCALLY_ACCEPTANCE("PYA"),
    BLOCKED("BCK"),
    DENIED_LOCALLY("DEN"),
    CANCELLED("CAN"),
    CONNECTED("CTC"),
    DISCONNECTED_LOCALLY("DSL"),
    DISCONNECTED_REMOTELY("DSR"),
    PENDING_REMOTELY_ACCEPTANCE("PRA");

    private String code;

    CryptoBrokerContactState(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CryptoBrokerContactState getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "PYA":
                return CryptoBrokerContactState.PENDING_LOCALLY_ACCEPTANCE;
            case "BCK":
                return CryptoBrokerContactState.BLOCKED;
            case "DEN":
                return CryptoBrokerContactState.DENIED_LOCALLY;
            case "CAN":
                return CryptoBrokerContactState.CANCELLED;
            case "CTC":
                return CryptoBrokerContactState.CONNECTED;
            case "DIS":
                return CryptoBrokerContactState.DISCONNECTED_LOCALLY;
            case "DSR":
                return CryptoBrokerContactState.DISCONNECTED_REMOTELY;
            case "PRA":
                return CryptoBrokerContactState.PENDING_REMOTELY_ACCEPTANCE;

            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ContactState enum");
        }
    }


}
