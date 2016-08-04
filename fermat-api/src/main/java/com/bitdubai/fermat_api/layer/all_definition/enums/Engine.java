package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.11.16..
 */
public enum Engine {

    BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY("BWCIUC"),
    BITCOIN_WALLET_CALL_INTRA_USER_IDENTITY("BWCIUI");

    private String code;

    Engine(String code) {
        this.code = code;
    }


    public static Engine getByCode(String code) throws InvalidParameterException {

        for (Engine vault : Engine.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This code is not valid for the CryptoCurrencyVault enum.");
    }

    public String getCode() {
        return this.code;
    }

}
