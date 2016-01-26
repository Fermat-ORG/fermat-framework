package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by mati on 2016.01.21..
 */
public enum CryptoTransmissionProtocolState {

    PRE_PROCESSING_SEND("PCS"),
    SENT("SNT"),
    PROCESSING_RECEIVE ("PRR"),
    RECEIVED("RCD"),
    WAITING_FOR_RESPONSE("WFR"),
    DONE("D"),
    SENT_TO_COMMUNICATION_TEMPLATE("STCT");



    private String code;


    CryptoTransmissionProtocolState(String code) {
        this.code = code;
    }

    public static CryptoTransmissionProtocolState getByCode(String code) throws InvalidParameterException {

        for (CryptoTransmissionProtocolState cryptoTransmissionMetadataState : CryptoTransmissionProtocolState.values()) {
            if (cryptoTransmissionMetadataState.getCode().equals(code))
                return cryptoTransmissionMetadataState;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoCurrencyVault enum.");


    }

    public String getCode() {
        return code;
    }
}
