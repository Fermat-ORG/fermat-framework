package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType</code>
 * enumerates the different types of types that a crypto address exchange request can have.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/10/2015.
 */
public enum RequestType implements FermatEnum {

    RECEIVED ("REC"),
    SENT     ("SEN"),
    ;

    private String code;

    RequestType(String code) {
        this.code = code;
    }

    public static RequestType getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "REC": return RECEIVED;
            case "SEN": return SENT    ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the RequestType enum."
                );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}