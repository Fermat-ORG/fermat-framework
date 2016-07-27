package com.bitdubai.fermat_cht_api.layer.actor_connection.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public enum ActorConnectionNotificationType implements FermatEnum {
    ACTOR_CONNECTED("CTD"),
    CONNECTION_REQUEST_RECEIVED("CRR"),;


    private final String code;

    ActorConnectionNotificationType(final String code) {
        this.code = code;
    }

    public static ActorConnectionNotificationType getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "CTD":
                return ACTOR_CONNECTED;
            case "CRR":
                return CONNECTION_REQUEST_RECEIVED;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the ActorConnectionNotificationType enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
