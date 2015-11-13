package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.10.15..
 */
public enum MessageType {

    REQUEST("REQUEST"),
    CHAT("CHAT"),;


    private String code;


    MessageType(String code) {
        this.code = code;
    }


    public static MessageType getByCode(String code) throws InvalidParameterException {

        for (MessageType vault : MessageType.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoCurrencyVault enum.");
    }


    public String getCode() {
        return this.code;
    }


}
