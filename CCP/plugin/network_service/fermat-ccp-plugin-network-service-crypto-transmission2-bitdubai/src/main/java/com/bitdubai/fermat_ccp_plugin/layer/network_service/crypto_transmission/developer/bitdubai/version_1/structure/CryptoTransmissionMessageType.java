package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by mati on 2016.01.28..
 */
public enum CryptoTransmissionMessageType {

    METADATA("M"),RESPONSE("R");

    private final String code;

    CryptoTransmissionMessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }



    public static CryptoTransmissionMessageType getByCode(final String code) throws InvalidParameterException {

        switch (code){
            case "M":return METADATA;
            case "R": return RESPONSE;

            default: throw new InvalidParameterException(
                    "Code received: "+code,
                    "The code received is not valid for NetworkServiceType enum."
            );
        }

    }
}
