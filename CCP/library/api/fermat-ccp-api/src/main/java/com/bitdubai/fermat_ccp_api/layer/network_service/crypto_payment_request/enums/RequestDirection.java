package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.RequestDirection</code>
 * represents the direction of the Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public enum RequestDirection implements FermatEnum {

    OUTGOING("OUTGOING"),
    INCOMING("INCOMING");

    private String code;

    RequestDirection(String code) {
        this.code = code;
    }

    public static RequestDirection getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "OUTGOING": return OUTGOING;
            case "INCOMING": return INCOMING;

            default: throw new InvalidParameterException(
                    "Code Received: " + code,
                    "This code is not valid for the RequestDirection enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
