package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState</code>
 * enumerates the states of connection of a common Fermat Intra Actor.
 */
public enum ConnectionState implements FermatEnum {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BLOCKED                     ("BCK"),
    CANCELLED                   ("CAN"),
    CONNECTED                   ("CTC"),
    DENIED_LOCALLY("DEN"),
    DISCONNECTED_LOCALLY        ("DSL"),
    DISCONNECTED_REMOTELY       ("DSR"),
    PENDING_LOCALLY_ACCEPTANCE("PLA"),
    PENDING_REMOTELY_ACCEPTANCE ("PRA");

    private final String code;

    ConnectionState(final String code) {
        this.code = code;
    }

    public static ConnectionState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "BCK": return BLOCKED                    ;
            case "CAN": return CANCELLED                  ;
            case "CTC": return CONNECTED                  ;
            case "DEN":
                return DENIED_LOCALLY;
            case "DSL": return DISCONNECTED_LOCALLY       ;
            case "DSR": return DISCONNECTED_REMOTELY      ;
            case "PLA":
                return PENDING_LOCALLY_ACCEPTANCE;
            case "PRA": return PENDING_REMOTELY_ACCEPTANCE;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The code received is not valid for the ContactState enum"
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
