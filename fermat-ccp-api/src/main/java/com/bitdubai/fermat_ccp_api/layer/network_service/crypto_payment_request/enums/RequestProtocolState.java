package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.RequestProtocolState</code>
 * represents the protocol state of the Crypto Payment Request in the network service.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public enum RequestProtocolState implements FermatEnum {

    PENDING_ACTION   ("PEA"),
    PROCESSING       ("PCS"),
    WAITING_RESPONSE ("WRE");

    private String code;

    RequestProtocolState(String code){
        this.code = code;
    }

    public RequestProtocolState getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "PEA": return PENDING_ACTION  ;
            case "PCS": return PROCESSING      ;
            case "WRE": return WAITING_RESPONSE;

            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
