package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.10.06..
 */
public enum CryptoTransmissionMetadataType {

    METADATA_SEND("MS"),
    METADATA_RECEIVE("MR");

    String code;

    CryptoTransmissionMetadataType(String code) {
        this.code = code;
    }


    public static CryptoTransmissionMetadataType getByCode(String code) throws InvalidParameterException {

        for (CryptoTransmissionMetadataType cryptoTransmissionStates : CryptoTransmissionMetadataType.values()) {
            if (cryptoTransmissionStates.getCode().equals(code))
                return cryptoTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoTransmissionMetadataType enum.");
    }

    public static CryptoTransmissionMetadataType getByCryptoTransmissionStates(CryptoTransmissionMetadataType cryptoTransmissionStates) throws InvalidParameterException {

        for (CryptoTransmissionMetadataType states : CryptoTransmissionMetadataType.values()) {
            if (states.getCode().equals(cryptoTransmissionStates))
                return cryptoTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "CryptoTransmissionMetadataType: " + cryptoTransmissionStates, "This CryptoTransmissionMetadataType is not valid for the CryptoTransmissionMetadataType enum.");
    }

    public String getCode(){
        return this.code;
    }


}
