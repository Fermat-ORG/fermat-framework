package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType</code>
 * enumerates the different types in which a crypto broker ans request could be.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
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