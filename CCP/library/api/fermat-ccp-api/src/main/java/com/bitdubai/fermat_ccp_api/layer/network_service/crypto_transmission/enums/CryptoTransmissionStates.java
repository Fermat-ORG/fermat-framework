package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.10.05..
 */
public enum CryptoTransmissionStates {

    /**
     * Sending states
     */
    PRE_PROCESSING_SEND("PPS"),
    PROCESSING_SEND_COMMUNICATION_TEMPLATE("PSCT"),
    SENT("S"),
    SEEN_BY_DESTINATION_NETWORK_SERVICE("SBDNS"),
    SEEN_BY_DESTINATION_VAULT("SBDV"),
    CREDITED_IN_DESTINATION_WALLET("CIDW"),

    /**
     * Receiving states
     */
    SEEN_BY_OWN_NETWORK_SERVICE("SBONS"),
    SEEN_BY_OWN_VAULT("SBOV"),
    CREDITED_IN_OWN_WALLET("CIOW"),
    ;

    String code;

    CryptoTransmissionStates(String code) {
        this.code = code;
    }


    public static CryptoTransmissionStates getByCode(String code) throws InvalidParameterException {

        for (CryptoTransmissionStates cryptoTransmissionStates : CryptoTransmissionStates.values()) {
            if (cryptoTransmissionStates.getCode().equals(code))
                return cryptoTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the cryptoTransmissionStates enum.");
    }

    public static CryptoTransmissionStates getByCryptoTransmissionStates(CryptoTransmissionStates cryptoTransmissionStates) throws InvalidParameterException {

        for (CryptoTransmissionStates states : CryptoTransmissionStates.values()) {
            if (states.getCode().equals(cryptoTransmissionStates))
                return cryptoTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "cryptoTransmissionStates: " + cryptoTransmissionStates, "This cryptoTransmissionStates is not valid for the cryptoTransmissionStates enum.");
    }

    public String getCode(){
        return this.code;
    }


}
