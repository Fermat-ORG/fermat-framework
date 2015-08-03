package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.enums.CryptoTransmissionState</code>
 * represent the state of a message sent by the network service Crypto Transmission.
 */
public enum CryptoTransmissionState {
    PROCESSING ("PCS"),
    RECEIVED_BY_DESTINATION ("RBD"),
    SENT ("SNT");

    private String code;

    CryptoTransmissionState(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public CryptoTransmissionState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "PCS": return CryptoTransmissionState.PROCESSING;
            case "RBD": return CryptoTransmissionState.RECEIVED_BY_DESTINATION;
            case "SNT": return CryptoTransmissionState.SENT;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,null,"The code read was: "+ code,"Missing branch in switch statement");
        }
    }
}
