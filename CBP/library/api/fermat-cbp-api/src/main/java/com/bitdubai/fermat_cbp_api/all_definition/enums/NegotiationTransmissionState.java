package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 30.11.15.
 */
public enum NegotiationTransmissionState implements FermatEnum {
    DONE                         ("DON"), // final state of request.
    PENDING_LOCAL_ACTION         ("PLA"), // pending local action
    PENDING_REMOTE_ACTION        ("PRA"), // waiting response from the counterpart
    PROCESSING_RECEIVE           ("PCR"), // when an action from the network service is needed receiving.
    PROCESSING_SEND              ("PCS"), // when an action from the network service is needed sending.
    WAITING_RECEIPT_CONFIRMATION ("WRC"), // the ns waits for the receipt confirmation of the counterpart.
    ;

    private String code;

    NegotiationTransmissionState(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NegotiationTransmissionState getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DON": return DONE                        ;
            case "PLA": return PENDING_LOCAL_ACTION        ;
            case "PRA": return PENDING_REMOTE_ACTION       ;
            case "PCR": return PROCESSING_RECEIVE          ;
            case "PCS": return PROCESSING_SEND             ;
            case "WRC": return WAITING_RECEIPT_CONFIRMATION;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NegotiationTransmissionState enum");
        }
    }

}
