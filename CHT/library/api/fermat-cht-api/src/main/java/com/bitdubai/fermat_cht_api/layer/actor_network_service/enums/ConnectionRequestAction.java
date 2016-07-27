package com.bitdubai.fermat_cht_api.layer.actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public enum ConnectionRequestAction implements FermatEnum {
    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    ACCEPT("ACC"), // accept a connection request.
    CANCEL("CAN"), // cancel a connection request.
    DENY("DEN"), // deny a connection request.
    DISCONNECT("DIS"), // disconnect from a crypto broker.
    NONE("NON"), // no action needed.
    REQUEST("REQ"), // created the connection request.

    ;

    private final String code;

    ConnectionRequestAction(final String code) {
        this.code = code;
    }

    public static ConnectionRequestAction getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "ACC":
                return ACCEPT;
            case "CAN":
                return CANCEL;
            case "DEN":
                return DENY;
            case "DIS":
                return DISCONNECT;
            case "NON":
                return NONE;
            case "REQ":
                return REQUEST;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the ConnectionRequestAction enum."
                );
        }

    }

    @Override
    public final String getCode() {
        return this.code;
    }
}
