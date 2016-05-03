package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.10.04..
 */
public enum CryptoTransmissionMetadataState {

    SEEN_BY_DESTINATION_NETWORK_SERVICE("SBDNS"),
    SEEN_BY_DESTINATION_VAULT("SBDV"),
    CREDITED_IN_DESTINATION_WALLET("CIDW"),

    SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE("SBONSWFR"),
    SEEN_BY_OWN_VAULT("SBOV"),
    CREDITED_IN_OWN_WALLET("CIOW");


    private String code;


    CryptoTransmissionMetadataState(String code) {
        this.code = code;
    }

    public static CryptoTransmissionMetadataState getByCode(String code) throws InvalidParameterException {

        for (CryptoTransmissionMetadataState cryptoTransmissionMetadataState : CryptoTransmissionMetadataState.values()) {
            if (cryptoTransmissionMetadataState.getCode().equals(code))
                return cryptoTransmissionMetadataState;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoCurrencyVault enum.");


    }

    public String getCode() {
        return code;
    }
}
