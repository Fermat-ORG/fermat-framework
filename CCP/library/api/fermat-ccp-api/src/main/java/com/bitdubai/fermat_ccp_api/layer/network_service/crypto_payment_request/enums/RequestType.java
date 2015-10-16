package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.RequestType</code>
 * represents the type of the Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public enum RequestType implements FermatEnum {

    RECEIVED("RECV"),
    SENT    ("SENT"),

    ;

    private String code;

    RequestType(String code) {
        this.code = code;
    }

    public static RequestType getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "RECV": return RECEIVED;
            case "SENT": return SENT    ;

            default: throw new InvalidParameterException(
                    "Code Received: " + code,
                    "This code is not valid for the RequestType enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
