package com.bitdubai.fermat_bch_api.layer.crypto_vault.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 10/9/15.
 */
public enum CryptoVaults {
    ASSETS_OVER_BITCOIN("AOB"),
    BITCOIN_CURRENCY("BTC"),
    BITCOIN_WATCH_ONLY("BWO"),
    IMPORTED_SEED("IMS"),
    FERMAT_CURRENCY("FER");


    private String code;

    CryptoVaults(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CryptoVaults getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AOB":
                return ASSETS_OVER_BITCOIN;
            case "BTC":
                return BITCOIN_CURRENCY;
            case "BWO":
                return BITCOIN_WATCH_ONLY;
            case "IMS":
                return IMPORTED_SEED;
            case "FER":
                return FERMAT_CURRENCY;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoVaults enum.");
        }
    }
}
