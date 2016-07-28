package com.bitdubai.fermat_api.layer.actor_connection.common.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState</code>
 * enumerates the states of connection of a common Fermat Actor.
 */
public enum ConnectionState implements FermatEnum, Serializable {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    BLOCKED_LOCALLY("BKL"),
    BLOCKED_REMOTELY("BKR"),
    CANCELLED_LOCALLY("CAL"),
    CANCELLED_REMOTELY("CAR"),
    CONNECTED("CTD"),
    NO_CONNECTED("NCTD"),
    DENIED_LOCALLY("DNL"),
    DENIED_REMOTELY("DNR"),
    DISCONNECTED_LOCALLY("DSL"),
    DISCONNECTED_REMOTELY("DSR"),
    ERROR("ERR"),
    INTRA_USER_NOT_FOUND("IUNF"),
    PENDING_LOCALLY_ACCEPTANCE("PLA"),
    PENDING_REMOTELY_ACCEPTANCE("PRA");

    private final String code;

    ConnectionState(final String code) {
        this.code = code;
    }

    public static ConnectionState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "BKL":
                return BLOCKED_LOCALLY;
            case "BKR":
                return BLOCKED_REMOTELY;
            case "CAL":
                return CANCELLED_LOCALLY;
            case "CAR":
                return CANCELLED_REMOTELY;
            case "CTD":
                return CONNECTED;
            case "DNL":
                return DENIED_LOCALLY;
            case "DNR":
                return DENIED_REMOTELY;
            case "DSL":
                return DISCONNECTED_LOCALLY;
            case "DSR":
                return DISCONNECTED_REMOTELY;
            case "ERR":
                return ERROR;
            case "PLA":
                return PENDING_LOCALLY_ACCEPTANCE;
            case "PRA":
                return PENDING_REMOTELY_ACCEPTANCE;
            case "IUNF":
                return INTRA_USER_NOT_FOUND;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code received is not valid for the ConnectionState enum"
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
