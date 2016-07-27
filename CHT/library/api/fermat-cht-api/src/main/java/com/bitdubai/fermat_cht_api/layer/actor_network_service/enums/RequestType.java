package com.bitdubai.fermat_cht_api.layer.actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public enum RequestType implements FermatEnum {
    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    RECEIVED("REC"),
    SENT("SEN"),;

    private final String code;

    RequestType(final String code) {
        this.code = code;
    }

    public static RequestType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "REC":
                return RECEIVED;
            case "SEN":
                return SENT;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the RequestType enum."
                );
        }

    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
