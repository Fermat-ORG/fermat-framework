package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.RequestProtocolState</code>
 * represents the protocol state of the crypto addresses exchange request in the network service.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public enum ProtocolState implements FermatEnum {

    DONE                         ("DON"), // final state of request.
    PENDING_ACTION               ("PEA"), // pending local action, is given after raise a crypto addresses event.
    PROCESSING_SEND              ("PCS"), // when an action from the network service is needed sending.
    WAITING_RESPONSE             ("WRE"), // waiting response from the counterpart.

    ;

    private String code;

    ProtocolState(String code){
        this.code = code;
    }

    public static ProtocolState getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "DON": return DONE                        ;
            case "PEA": return PENDING_ACTION              ;
            case "PCS": return PROCESSING_SEND             ;
            case "WRE": return WAITING_RESPONSE            ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the ProtocolState enum"
                );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
