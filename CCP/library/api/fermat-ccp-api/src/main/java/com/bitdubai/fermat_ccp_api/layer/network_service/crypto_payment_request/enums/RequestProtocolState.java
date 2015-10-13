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

    DONE             ("DON"), // final state of request.
    PENDING_ACTION   ("PEA"), // pending local action, is given after raise a crypto payment request event.
    PROCESSING       ("PCS"), // when an action from the network service is needed (maybe send, maybe raise an event) depends on request direction.
    WAITING_RESPONSE ("WRE")  // waiting response from the counterpart.

    ;

    private String code;

    RequestProtocolState(String code){
        this.code = code;
    }

    public static RequestProtocolState getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "DON": return DONE            ;
            case "PEA": return PENDING_ACTION  ;
            case "PCS": return PROCESSING      ;
            case "WRE": return WAITING_RESPONSE;

            default:
                throw new InvalidParameterException(
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
