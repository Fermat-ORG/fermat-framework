package com.bitdubai.fermat_bch_api.layer.crypto_network.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 12/31/15.
 */
public enum Status {
    BROADCASTED("BTED"),
    BROADCASTING("BING"),
    CANCELLED("CNL"),
    IDLE("IDLE");


    private String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Status getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BTED":
                return BROADCASTED;
            case "BING":
                return BROADCASTING;
            case "CNL":
                return CANCELLED;
            case "IDLE":
                return IDLE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the Broadcasting Status enum.");
        }
    }
}
