package com.bitdubai.fermat_art_api.layer.actor_connection.fan.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public enum FanActorConnectionNotificationType implements FermatEnum {


    /**
     * To do make code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ACTOR_CONNECTED              ("AARTCTD"),
    CONNECTION_REQUEST_RECEIVED  ("AFANCRR"),

    ;

    private final String code;

    FanActorConnectionNotificationType(final String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static FanActorConnectionNotificationType getByCode(String code)
            throws InvalidParameterException {
        for (FanActorConnectionNotificationType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the FanActorConnectionNotificationType enum.");
    }

    @Override
    public String toString() {
        return "FanActorConnectionNotificationType{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return this.code;
    }

}
