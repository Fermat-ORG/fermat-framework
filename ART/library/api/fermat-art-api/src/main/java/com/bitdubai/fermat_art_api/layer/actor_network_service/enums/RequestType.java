package com.bitdubai.fermat_art_api.layer.actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public enum RequestType implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    RECEIVED ("REC"),
    SENT     ("SEN"),

            ;

    private final String code;

    RequestType(final String code) {
        this.code = code;
    }

    //Public Methods

    public static RequestType getByCode(String code) throws InvalidParameterException {
        for (RequestType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the RequestType enum.");
    }

    //Getter and setters
    @Override
    public final String getCode() {
        return this.code;
    }

}
