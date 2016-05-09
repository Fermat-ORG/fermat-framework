package com.bitdubai.fermat_art_api.layer.actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 *
 */
public enum ProtocolState implements FermatEnum {

    DONE                         ("DON"), // final state of request.
    PENDING_LOCAL_ACTION         ("PLA"), // pending local action
    PENDING_REMOTE_ACTION        ("PRA"), // waiting response from the counterpart
    PROCESSING_RECEIVE           ("PCR"), // when an action from the network service is needed receiving.
    PROCESSING_SEND              ("PCS"), // when an action from the network service is needed sending.
    WAITING_RECEIPT_CONFIRMATION ("WRC"), // the ns waits for the receipt confirmation of the counterpart.

    ;

    private final String code;

    ProtocolState(final String code){
        this.code = code;
    }

    public static ProtocolState getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "DON": return DONE                        ;
            case "PLA": return PENDING_LOCAL_ACTION        ;
            case "PRA": return PENDING_REMOTE_ACTION       ;
            case "PCR": return PROCESSING_RECEIVE          ;
            case "PCS": return PROCESSING_SEND             ;
            case "WRC": return WAITING_RECEIPT_CONFIRMATION;

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
